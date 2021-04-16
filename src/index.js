var http = require('http');
var express = require('express');

var app = express();
app.use('/', express.static(__dirname + '/'));

app.get('/', function(request, response){
	response.sendFile('index.html', {root: __dirname });
});

app.listen(3000, () => {
  console.log("Server started");
});