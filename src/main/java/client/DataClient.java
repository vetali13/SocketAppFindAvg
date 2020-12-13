package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataClient {
	
	public enum Sign {
	    POSITIVE, NEGATIVE 
	}
	
	public List<Integer> data;
	
	public Sign sign;
	

	public DataClient(	@JsonProperty("data") List<Integer> data, 
						@JsonProperty("sign") Sign sign
					){
		super();
		this.data = data;
		this.sign = sign;
	}

	
	public static void getData(List<Integer> list, Sign sign) throws UnknownHostException, IOException {
		
		// Starting the client socket
		System.out.println("CLIENT >>> starting");
		Socket clientSocket = new Socket("localhost", 7777);
		System.out.println("CLIENT >>> sending");
		
		// Starting the output and input streams
		DataOutputStream dout = new DataOutputStream( clientSocket.getOutputStream() );
		DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
		
		// Creating Object of ObjectMapper define in Jakson Api
        ObjectMapper Obj = new ObjectMapper();
		
		// instantiating a new DataClient object with the list from method arguments
		DataClient dc = new DataClient(list, sign);

	    // get DataClient object as a json string
	    String jsonStr = Obj.writeValueAsString(dc);
	    dout.writeUTF(jsonStr);
		
		// receiving the result stream from the server and saving it to a String
		String json = dis.readUTF();
		
		// extracting the value of the field "avg" from json
		JsonNode jsonNode = Obj.readTree(json);
		String result = jsonNode.get("avg").asText();
		
		// ending the session
		System.out.println("CLIENT >>> ending");
		dout.flush();
		clientSocket.close();
		
		// printing the result
		System.out.println("Result is: " + result);
		
	}
	
	public static void main(String[] args) throws IOException {
		
		List<Integer> list = new ArrayList<>(Arrays.asList(20, 30, 40, 50));
		
		DataClient.getData(list, Sign.POSITIVE);

	}

}
