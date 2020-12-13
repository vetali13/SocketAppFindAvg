package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import client.DataClient;
import client.DataClient.Sign;

public class DataServer {
	
	public static void start() throws IOException {
System.out.println("SERVER >>> starting");
		
		// Starting the server socket
		ServerSocket serverSocket = new ServerSocket(7777);
		
		// wait for the connection
		System.out.println("SERVER >>> waiting");
		Socket clientSocket = serverSocket.accept();
		
		// starting the input stream
		DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
		
		// reading the input stream from the client and saving it to a String
		String input = dis.readUTF();

		// Creating Object of ObjectMapper defined in Jackson Api
        ObjectMapper objectMapper = new ObjectMapper();
		DataClient dc = objectMapper.readValue(input, DataClient.class);	
		
		// Calculating the average
		Integer average = 0;
		Integer count = 0;
		if (dc.sign == Sign.POSITIVE) {
			
			for (Integer i : dc.data) {
				if (i >= 0) {
					average += i;
					count++;
				}
			}
			
		} else {
			for (Integer i : dc.data) {
				if (i < 0) {
					average += i;
					count++;
				}
			
			}
			
		}
		
		average = average / count;

		// Building the result json to be sent to client
		ObjectNode result = objectMapper.createObjectNode();
	    result.put("avg", average);
	    String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
		
		// starting the output stream
		DataOutputStream dout = new DataOutputStream( clientSocket.getOutputStream() );
		
		// writing the average to the output stream
		dout.writeUTF(json);
		
		// closing the streams
		dout.flush();
		dis.close();
		
		// closing server socket
		System.out.println("SERVER >>> ending");
		serverSocket.close();
	}

	public static void main(String[] args) throws IOException {	
		start();
	}
	
}
