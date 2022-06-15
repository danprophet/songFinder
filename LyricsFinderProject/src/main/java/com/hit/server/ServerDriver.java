package com.hit.server;
import java.lang.Thread;

public class ServerDriver {
	public static void main(String[] args) 
	{
		Server server = new Server(34567);
		new Thread(server).start();
	}
}