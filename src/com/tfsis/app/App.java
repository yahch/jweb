package com.tfsis.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tfsis.server.AppServer;

public class App {

	static java.util.logging.Logger log = Logger.getLogger("App");

	public static void main(String[] args) {

		log.log(Level.INFO, "starting");
		try {
			new AppServer(8600);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}

	}

}
