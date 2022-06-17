package com.hit.server;
import java.io.IOException;
import java.lang.Thread;

public class ServerDriver {
	public static void main(String[] args) 
	{
		Server server;
		try
		{
			server = new Server(34567);
			new Thread(server).start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}