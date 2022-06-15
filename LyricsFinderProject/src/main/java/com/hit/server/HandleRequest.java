package com.hit.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HandleRequest implements Runnable {
	Socket mySocket;

	public HandleRequest(Socket inSocket)
	{ 
		this.mySocket = inSocket;
	}

	public Request parseRequest(String data)
	{
		// TODO: this
		Request clientReq = new Request();
		return clientReq;
	}
	
	@Override
	public void run() 
	{
		System.out.println("Server - Handle Request now");
		String str = "";
		
		while  (str.equals("stop") == false)
		{
			try {
			System.out.println(">> entered while");
			BufferedReader input = new BufferedReader(new InputStreamReader(this.mySocket.getInputStream()));
			PrintWriter output = new PrintWriter(this.mySocket.getOutputStream());
			
			str = input.readLine();
			System.out.println(">> request data read from user"+str);
			
			Request clientReq = parseRequest(str);
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	

		
}
