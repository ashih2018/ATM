const Tesseract = require('tesseract.js');
const fs = require('fs');
const readline = require('readline');
const mongoose = require('mongoose');
const Cheque = require('./cheque.model');
const {
    google
} = require('googleapis');

// For full access to account
const SCOPES = ['https://mail.google.com/'];
const TOKEN_PATH = 'token.json';

let db_url = 'mongodb+srv://csc207:bankdaddy1234@csc207-cviap.mongodb.net/test?retryWrites=true';
mongoose.connect(db_url);
mongoose.Promise = global.Promise;
let db = mongoose.connection;

// Connection events
db.on('error', console.error.bind(console, 'MongoDB connection error:'));
db.on('connected', function() {
    console.log('Connected to database');
});
db.on('disconnected', function() {
    console.log('Disconnected from database');
});

/**
 * Create an OAuth2 client with the given credentials, and then execute the
 * given callback function.
 * @param {Object} credentials The authorization client credentials.
 * @param {function} callback The callback to call with the authorized client.
 */
function authorize(credentials, callback) {
    const {
        client_secret,
        client_id,
        redirect_uris
    } = credentials.web;
    const oAuth2Client = new google.auth.OAuth2(
        client_id, client_secret, redirect_uris[0]);

    // Check if we have previously stored a token.
    fs.readFile(TOKEN_PATH, (err, token) => {
        if (err) return getNewToken(oAuth2Client, callback);
        oAuth2Client.setCredentials(JSON.parse(token));
        callback(oAuth2Client);
    });
}

/**
 * Get and store new token after prompting for user authorization, and then
 * execute the given callback with the authorized OAuth2 client.
 * @param {google.auth.OAuth2} oAuth2Client The OAuth2 client to get token for.
 * @param {getEventsCallback} callback The callback for the authorized client.
 */
function getNewToken(oAuth2Client, callback) {
    const authUrl = oAuth2Client.generateAuthUrl({
        access_type: 'offline',
        scope: SCOPES,
    });
    console.log('Authorize this app by visiting this url:', authUrl);
    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout,
    });
    rl.question('Enter the code from that page here: ', (code) => {
        rl.close();
        oAuth2Client.getToken(code, (err, token) => {
            if (err) return console.error('Error retrieving access token', err);
            oAuth2Client.setCredentials(token);
            // Store the token to disk for later program executions
            fs.writeFile(TOKEN_PATH, JSON.stringify(token), (err) => {
                if (err) return console.error(err);
                console.log('Token stored to', TOKEN_PATH);
            });
            callback(oAuth2Client);
        });
    });
}

function listMessages(auth) {
    const gmail = google.gmail({version: 'v1', auth});
    gmail.users.messages.list({
        userId: 'me',
        q: "to: csc207.bank0354@gmail.com",
    }, (err, res) => {
        if (err) console.log('The API returned an error while getting messages list: ' + err);
        //console.log("sdsd" + res.data.messages);
        messagesList = res.data.messages
        if (messagesList.length) {

            //Process first message; remove .splice(0,1) to process all messages
            messagesList.forEach(message => {
                gmail.users.messages.get({
                    userId: 'me',
                    id: message.id,
                }, (err, res) => {
                    if (err) console.log('Could not get attachmentid');

                    // Get attachment for each message
                    attId = res.data.payload.parts[1].body.attachmentId;
                    if (attId) {
                        gmail.users.messages.attachments.get({
                            userId: 'me',
                            messageId: message.id,
                            id: attId,
                        }, (err, res) => {
                            if (err) console.log('Could not get attachment');
                            else {
                                // Analyze attachment
                                imageAs64bitString = res.data.data;
                                buff = new Buffer(imageAs64bitString, 'base64');
                                fs.writeFileSync('image.png', buff);

                                Tesseract.recognize('./image.png')
                                    .then(function(res) {
                                        console.log(`The result is: \n${res.text}`)

                                        from = res.text.split('\n')[0].replace('From: ', '');
                                        to = res.text.split('\n')[1].replace('To: ', '');
                                        amount = res.text.split('\n')[2].replace('Amount in dollars: ', '');

                                        // Create and save to database
                                        let item = new Cheque({
                                            to: to,
                                            from: from,
                                            amount: amount,
                                        })
                                        item.save(function(err) {
                                            if (err) return next(err);
                                        });

                                        // After processing message, delete it
                                        gmail.users.messages.delete({
                                            userId: 'me',
                                            id: message.id,
                                        }, (err, res) => {
                                            if (err) console.log('Could not delete message: ' + err);
                                            console.log('Deleted message: ' + message.id);
                                        })
                                    })
                            }
                        })
                    }
                });
            })
        }
    });
}

// ################################################
// Main block that runs everything
// Load client secrets from a local file.
function analyzeMessages() {
    fs.readFile('credentials.json', (err, content) => {
        if (err) return console.log('Error loading client secret file:', err);
        // Authorize a client with credentials, then call the Gmail API.
        authorize(JSON.parse(content), listMessages);
    });
}
// ################################################

// Checks for emailed cheques every 1 minute
var minutes = 1;
var interval = minutes*60*1000;
setInterval(() => {
    console.log('Doing check');
    analyzeMessages();
}, interval);
