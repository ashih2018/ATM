const mongoose = require('mongoose');
const Schema = mongoose.Schema;

let cheque = new Schema({
    to: {type: String, required: true},
    from: {type: String, required: true},
    amount: {type: Number, required: true}
});

module.exports = mongoose.model('Cheque', cheque);