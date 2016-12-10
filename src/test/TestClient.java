package test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.Assert;
import org.junit.Test;

import client.Client;
import junit.framework.TestCase;

public class TestClient extends TestCase{
	
	private Client client;
	private Socket socket;
	
    /*@Before
    public void initialize() {
        client= new Client();
    }*/
	
	/*public TestClient(){
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
		client = new Client();
		socket = new Socket("127.0.0.1", 1984);
	}
	
	protected void tearDown() throws Exception{
		super.tearDown();
		client = null;
		socket = null;
	}*/
	
	
    /*@Test
	public void testClient(){
		assertNotNull("instance créée", client);
	}*/
	
	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

	@Test
	public void testConnect() throws IOException {
        /*client= new Client();
		Socket socketTest = new Socket("127.0.0.1",1984);
		assertEquals(socketTest, client.Connect("127.0.0.1", 1984));*/	
		
		final Client client = mock(Client.class);
		final Socket socket = mock(Socket.class);
		Assert.assertEquals("Connexion OK", client.Connect("127.0.0.1", 1984));
		
	}

	@Test
	public void testSendMessage() throws IOException {	
		final Client client = mock(Client.class);
		final Socket socket = mock(Socket.class);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        Assert.assertTrue("Message sent successfully", client.sendMessage(socket, "test"));
        Assert.assertEquals("test".getBytes(), byteArrayOutputStream.toByteArray());

	}

	@Test
	public void testReceiveMessage() {
		fail("Not yet implemented");
	}

}
