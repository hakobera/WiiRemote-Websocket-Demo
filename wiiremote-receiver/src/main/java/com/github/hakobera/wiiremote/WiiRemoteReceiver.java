package com.github.hakobera.wiiremote;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import wiiremotej.WiiRemote;
import wiiremotej.event.WRAccelerationEvent;
import wiiremotej.event.WRButtonEvent;
import wiiremotej.event.WRStatusEvent;
import wiiremotej.event.WiiRemoteAdapter;

public class WiiRemoteReceiver extends WiiRemoteAdapter {

	private WiiRemote remote;
	
	private Socket socket;
	
	private PrintWriter output;
	
	private long prevTime;
	
	public WiiRemoteReceiver(WiiRemote remote) throws UnknownHostException, IOException {
		this.remote = remote;
		socket = new Socket("127.0.0.1", 9999);
		output = new PrintWriter(socket.getOutputStream());
		prevTime = System.currentTimeMillis();
	}
	
	public void cleanup() {
		if (socket != null) {
			if (output != null) {
				output.close();
			}

			try {
				socket.close();
				System.out.println("WiiRemoteReceiver cleanuped.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void accelerationInputReceived(WRAccelerationEvent event) {
		long now = System.currentTimeMillis();
		long interval = now - prevTime;
		if (interval > 300) {
			prevTime = now;
			String data = String.format("%f,%f", event.getPitch(), event.getRoll());
			System.out.println(data);
			output.write(data);
			output.flush();
		}
	}

	@Override
	public void buttonInputReceived(WRButtonEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnected() {
        System.out.println("Remote disconnected... Please Wii again.");
        System.exit(1);
	}

	@Override
	public void statusReported(WRStatusEvent event) {
        System.out.printf("Battery level: %f%%\n",  (double) event.getBatteryLevel()/2);
        System.out.printf("Continuous: %s\n", event.isContinuousEnabled());
        System.out.printf("Remote continuous: %s\n", remote.isContinuousEnabled());
	}

}
