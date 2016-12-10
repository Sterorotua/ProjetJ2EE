package client;

import java.io.IOException;
import java.util.Properties;

public class ClientConfig {

	private String serverAddr;
	private int serverPort;
	
	public ClientConfig() {
		Properties properties = new Properties();
		try {
			properties.load(Client.class.getResourceAsStream("ressources/config.properties"));
			serverAddr = properties.getProperty("SERVER_ADDRESS").trim();
			serverPort = Integer.parseInt(properties.getProperty("SERVER_PORT").trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getServerAddr() {
		return this.serverAddr;
	}
	
	public int getServerPort() {
		return this.serverPort;
	}
}
