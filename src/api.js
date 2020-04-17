var http = require('http');
var bodyParser = require('body-parser');
var express = require('express');
var config = require('./config.js');
var blockchain = require('./blockchain.js');

var app = express();
app.use(bodyParser.urlencoded({extended: false}));

/**
* Create a new identity for a new user
*
*/
app.post("/identity", function(req, res){

});

/**
* Create a new credential for a user
*/
app.post("/credential", function(req, res) {

})

/**
* Update a credential
*/
app.put("/credential", function(req, res) {

})

/**
* Revoke a credential
*/
app.get("/revoke", function(req, res) {

})

/**
* Check access for user to a given service
*/
app.get("/verify", function(req, res) {

})

app.listen(3000);