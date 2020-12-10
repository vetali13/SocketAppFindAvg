package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

	public static void main(String[] args) throws IOException {
		
		System.out.println("SERVER >>> starting");
		
		ServerSocket serverSocket = new ServerSocket(7777);
		// wait for the connection
		
		System.out.println("SERVER >>> waiting");
		
		Socket clientSocket = serverSocket.accept();
		
		DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
		
		System.out.println("SERVER >>> client sent: " + dis.readUTF());
		
		DataOutputStream dout = new DataOutputStream( clientSocket.getOutputStream() );
		
		dout.writeUTF("Hello back from the server!!!");
		
		dout.flush();
		dis.close();
		
		
		
		System.out.println("SERVER >>> ending");
		serverSocket.close();
	}

}
