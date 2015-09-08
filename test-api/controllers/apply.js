'use strict';

var errors = require('./errors.js');

// Register a user
exports.sendApp = function(req, res, next) {
    console.log(req.body);
    res.status(200).json({"message": "Applied!"});

};
