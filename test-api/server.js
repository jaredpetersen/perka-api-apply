'use strict';

var express = require('express');
var app = express();
var bodyParser = require('body-parser');

// BodyParser allows us to get data out of URLs
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

// Add in the routes
require('./routes')(app);

// Start the server
app.listen(8080);
console.log("Server running on port 8080");
