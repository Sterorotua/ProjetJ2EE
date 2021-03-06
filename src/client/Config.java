package client;

import java.io.IOException;
import java.util.Properties;

public class Config {

	private String serverAddr = "";
	
	Config() {
		Properties properties = new Properties();
		try {
			properties.load(Client.class.getResourceAsStream("ressources/config.properties"));
			serverAddr = properties.getProperty("SERVER_ADDRESS");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getServerAddr() {
		return this.serverAddr;
	}
}
