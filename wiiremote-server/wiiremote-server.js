var http = require('http');
var net = require('net');
var sys = require('sys');
var io = require('socket.io');
var constants = require('../wiiremote-client/constants.js');

// send to Browser
var server = http.createServer(function(in_req, in_res){
    in_res.writeHeader(200, {'Content-Type': 'text/html'});
    in_res.writeBody('connected');
    in_res.finish();
});
var socket = io.listen(server);

socket.on("connection", function(in_client){
    in_client.on("message", function(in_message) {
        sys.log("message: " + in_message);
    });
    in_client.on("disconnect", function(){
        sys.log("disconnected.");
    });
});
server.listen(constants.WIIREMOTE_PORT);

// receive from WiiRemoteJ
net.createServer(function(in_socket){
    var buff = "";
    in_socket.on("data", function(in_data){
        //sys.log(in_data);
        var angles = ("" + in_data.toString()).split(',');
        var out = {pitch: angles[0], roll:angles[1]};
        socket.broadcast(out);
    });
    in_socket.on('error', function (in_exc) {
        sys.log("ignoring exception: " + in_exc);
    });
}).listen(9999, "127.0.0.1");






