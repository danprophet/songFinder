package com.hit.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	ServerSocket mySocket;
	int incomingPort;
	int currentReqId;
	boolean serverStatus = true;
	
	public Server(int port) throws IOException {
		this.incomingPort = port;
		mySocket = new ServerSocket(this.incomingPort);
		currentReqId = 0;
	}
	
	public void run()
	{
		System.out.println("Server up, port [" + this.incomingPort + "]");
		while(serverStatus == true)
		{
			Socket receivedSocket;
			try 
			{
				receivedSocket = mySocket.accept();
				System.out.println("request["+currentReqId+"] incoming.");
				new Thread(new HandleRequest(receivedSocket)).start();
				currentReqId++;
			}
			catch (IOException e)
			{
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Closing Server.");
	}

}
