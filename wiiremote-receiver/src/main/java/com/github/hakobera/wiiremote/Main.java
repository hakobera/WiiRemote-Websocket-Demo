package com.github.hakobera.wiiremote;

import wiiremotej.WiiRemote;
import wiiremotej.WiiRemoteJ;
import wiiremotej.event.WRIREvent;

public class Main {

	public static void main(String[] args) throws Exception {
		System.setProperty("bluecove.jsr82.psm_minimum_off", "true");

		WiiRemoteJ.setConsoleLoggingAll();

		WiiRemote remote = null;
		while (remote == null) {
			try {
				remote = WiiRemoteJ.findRemote();
			} catch (Exception e) {
				remote = null;
				e.printStackTrace();
				System.out.println("Failed to connect remote. Trying again.");
			}
		}
		
		final WiiRemoteReceiver receiver = new WiiRemoteReceiver(remote); 
		remote.addWiiRemoteListener(receiver);
		remote.setAccelerometerEnabled(true);
		remote.setSpeakerEnabled(false);
		remote.setIRSensorEnabled(false, WRIREvent.BASIC);
		remote.setLEDIlluminated(0, true);

		final WiiRemote remoteF = remote;
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				receiver.cleanup();
				remoteF.disconnect();
			}
		}));
	}

}
