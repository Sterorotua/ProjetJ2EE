package test;

import static org.junit.Assert.fail;
import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.Assert;
import org.junit.Test;

import client.Client;

public class TestClient {

	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

	@Test
	public void testConnect() {
		fail("Not yet implemented");
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
