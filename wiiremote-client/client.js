var screen;
var ctx;
var angle = 0;

var onConnect = function() {
    console.log('connected');
};

var onMessage = function(data) {
    var v = parseFloat(data.pitch);
    console.log(v);
    if (!isNaN(v)) {
        angle = v;
    }
};

var update = function() {
    ctx.fillStyle = "#000";
    ctx.fillRect(0, 0, screen.width, screen.height);
    ctx.save();
    ctx.fillStyle = "#eee";
    ctx.translate(screen.width/2, screen.height/2);
    ctx.rotate(angle);
    ctx.fillRect(-100, -10, 200, 20);
    ctx.restore();
};

var init = function() {
    var full_domain = location.href.split('/')[2].split(':')[0];
    console.log(full_domain);
    var socket = new io.Socket(full_domain, { port: exports.WIIREMOTE_PORT });
    socket.on('connect', onConnect);
    socket.on('message', onMessage);
    socket.connect();

    screen = document.getElementById('screen');
    ctx = screen.getContext('2d');

    setInterval(update, 200);
};

window.addEventListener('DOMContentLoaded', init);