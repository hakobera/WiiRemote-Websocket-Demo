/*
 * Detect if executed on server or client. 
 */
if (typeof global != 'undefined') {
    // node.js server
} else {
    // client
    var exports = {};
}

exports.WIIREMOTE_PORT = 3000;


