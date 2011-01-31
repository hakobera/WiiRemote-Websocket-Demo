var onConnect = function() {
    console.log('connected');
};

var onMessage = function(data) {
    console.log(data);
};


var init = function() {
    var full_domain = location.href.split('/')[2].split(':')[0];
    console.log(full_domain);
    var socket = new io.Socket(full_domain, { port: exports.WIIREMOTE_PORT });
    socket.on('connect', onConnect);
    socket.on('message', onMessage);
    //socket.on('disconnect', function(){ … });
    socket.connect();
};

window.addEventListener('DOMContentLoaded', init);