const mongoose = require('mongoose');
const Cheque = require('./cheque.model');
const express = require('express');
const router = express.Router();
const bodyParser = require('body-parser');

const app = express();

let db_url = 'mongodb+srv://csc207:bankdaddy1234@csc207-cviap.mongodb.net/test?retryWrites=true';
mongoose.connect(db_url);
mongoose.Promise = global.Promise;
let db = mongoose.connection;

db.on('error', console.error.bind(console, 'MongoDB connection error:'));
db.on('connected', function() {
    console.log('Connected to database');
});
db.on('disconnected', function() {
    console.log('Disconnected from database');
});

router.get('/all', (req, res) => {
    Cheque.find({}, function (err, items) {
        if (err) return next(err);
        res.send(items);
    })
})

router.delete('/:id/delete', (req, res) => {
    Cheque.findByIdAndDelete(req.params.id, function (err, item) {
        if (err) return next(err);
        res.send(item);
    })
})

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use('/api', router);

let port = 1234;

app.listen(port, () => {
    console.log('Server running on port ' + port);
})