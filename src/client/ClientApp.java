package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientApp {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		System.out.println("CLIENT >>> starting");
		
		Socket clientSocket = new Socket("localhost", 7777);
		
		System.out.println("CLIENT >>> sending");
		
		DataOutputStream dout = new DataOutputStream( clientSocket.getOutputStream() );
		DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
		
		dout.writeUTF("Hello Server!!!");
		
		dout.flush();
		
		System.out.println("CLIENT >>> server sent back: " + dis.readUTF());
		
		System.out.println("CLIENT >>> ending");
		
		clientSocket.close();
	}

}
