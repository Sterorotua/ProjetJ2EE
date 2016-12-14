package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ClientConfig {

	private ArrayList <String> serversAddr;
	private ArrayList <Integer> serversPort;

	
	public ClientConfig() {
		Properties properties = new Properties();
		serversAddr = new ArrayList <String>();
		serversPort = new ArrayList <Integer>();
		try {
			properties.load(Client.class.getResourceAsStream("ressources/config.properties"));
			serversAddr.add(properties.getProperty("SERVER1_ADDRESS").trim());
			serversAddr.add(properties.getProperty("SERVER2_ADDRESS").trim());
			serversPort.add(Integer.parseInt(properties.getProperty("SERVER1_PORT").trim()));
			serversPort.add(Integer.parseInt(properties.getProperty("SERVER2_PORT").trim()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList <String> getServersAddr() {
		return this.serversAddr;
	}
	
	public ArrayList <Integer> getServersPort() {
		return this.serversPort;
	}
}
