var http = require('http');
var bodyParser = require('body-parser');
var express = require('express');
var config = require('./config.js');
var blockchain = require('./blockchain.js');

var app = express();
app.use(bodyParser.urlencoded({extended: false}));

app.post("/identity", function(req, res){

});

app.post("/credential", function(req, res) {

})

app.put("/credential", function(req, res) {

})

app.get("/revoke", function(req, res) {

})

app.get("/verify", function(req, res) {

})

app.listen(3000);