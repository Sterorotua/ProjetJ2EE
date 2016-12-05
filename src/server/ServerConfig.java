package server;

import java.io.IOException;
import java.util.Properties;

import client.Client;

public class ServerConfig {
private int serverPort;
private int nbUsersMax;
	
	public ServerConfig() {
		Properties properties = new Properties();
		try {
			properties.load(Server.class.getResourceAsStream("ressources/config.properties"));
			serverPort = Integer.parseInt(properties.getProperty("SERVER_PORT").trim());
			nbUsersMax = Integer.parseInt(properties.getProperty("NB_USERS_MAX").trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getServerPort() {
		return this.serverPort;
	}
	
	public int getNbUsersMax(){
		return this.nbUsersMax;
	}
}
