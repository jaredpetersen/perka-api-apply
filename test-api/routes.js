'use strict';

module.exports = function (app) {

    // Apply
    var apply = require('./controllers/apply');
    app.post('/apply', apply.sendApp);

    // Error Handling
    var errors = require('./controllers/errors');
    app.use(errors.errorHandler);
    app.use(errors.nullRoute); // Requested route doesn't exist

};
