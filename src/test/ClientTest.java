package test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import client.Client;
import junit.framework.TestCase;

public class ClientTest extends TestCase{

	private Client client;
	private Socket socket;
	
	@Before
	public void setUp() throws IOException {
		Client client = new Client();
		Socket socket = new Socket(InetAddress.getLocalHost(),0);
		socket.
	}
	
	@Test
	public void testReceptionMessage() throws Exception {
		super.setUp();
		assertEquals("",client.receptionMessage(socket));
	}
	
	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

	@Test
	public void testConnect() {
		fail("Not yet implemented");
	}

	@Test
	public void testEnvoiMessage() {
		fail("Not yet implemented");
	}



}
