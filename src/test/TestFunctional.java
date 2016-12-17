package test;

import client.Client;
import server.Server;

public class TestFunctional {

	public static void main(String[] args) {

		Thread threadServer = new Thread() {
			public void run() {
				new Server();
			}
		};
		threadServer.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Thread threadClient = new Thread() {
			public void run() {
				Client client = new Client();
				client.connect(0);
			}
		};

		threadClient.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Thread threadClient2 = new Thread() {
			public void run() {
				Client client = new Client();
				client.connect(0);
			}
		};

		threadClient2.start();

		Thread threadClient3 = new Thread() {
			public void run() {
				Client client = new Client();
				client.connect(0);
				client.finalize();
			}
		};

		threadClient3.start();

		//Robustness test
		
		for (int i = 0; i < 102; ++i) {
			Thread threadClient4 = new Thread() {
				public void run() {
					Client client = new Client();
					client.connect(0);
				}
			};

			threadClient4.start();

		}

	}
}
