package com.hit.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hit.dm.Song;

public class HandleRequest implements Runnable {
	Socket mySocket;

	public HandleRequest(Socket inSocket)
	{ 
		this.mySocket = inSocket;
	}

	public Request parseRequest(String data)
	{
		// TODO: this
		JsonDeserializer<Request> deserializer = new JsonDeserializer<Request>()
		{
			@Override
			public Request deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
					throws JsonParseException {
				JsonObject jsonObject = arg0.getAsJsonObject();
				JsonObject headers = jsonObject.get("headers").getAsJsonObject(); // the command
				JsonObject body = jsonObject.get("body").getAsJsonObject(); // the contenet of the ewquest
				String command = headers.get("command").getAsString();
				Request thisRequest = null;
				// Check which kind of request:
				switch(command)
				{
				case "add":
					String title = body.get("title").getAsString();
					String artist = body.get("artist").getAsString();
					String lyrics = body.get("lyrics").getAsString();
					thisRequest = new Request(command, title, artist, lyrics);
					break;
				case "remove":
					int id = body.get("id").getAsInt();
					thisRequest = new Request(command, id);
					break;
				case "search_title":
				case "search_artist":
				case "search_lyrics":
					{
						String searchPattern = body.get("string").getAsString();
						thisRequest = new Request(command, searchPattern);

					}
				default:
					System.out.println("[SERVER] Request error");
					break;
				}

				System.out.println("[SERVER] Client Request Parsed");
				return thisRequest;
			}
		};
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Request.class, deserializer);
		Gson myGson = gsonBuilder.create();
		Request clientReq = myGson.fromJson(data, Request.class);
		
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
			Response serverRes;
			// pass to controller here:
			switch (clientReq.getCommand())
			{
				case "add":
					break;
				default:
					break;
			}

				
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	

		
}
