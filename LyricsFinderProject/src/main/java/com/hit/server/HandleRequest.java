package com.hit.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hit.controller.Controller;
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
				JsonObject body = jsonObject.get("body").getAsJsonObject(); // the contenet of the request
				String command = headers.get("action").getAsString();
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
					break;
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
	
	public Response requestControllerResponse(Request request)
	{
		Response returnRes = null;
		Controller controller = new Controller();
		switch (request.getCommand())
		{
			case "add":
				System.out.println("[Server] Add command");
				returnRes = new Response(request.getCommand(), controller.add(new Song(request.getTitle(), request.getArtist(), request.getLyrics())));
				break;
			case "remove":
				System.out.println("[Server] Remove command");
				returnRes = new Response(request.getCommand(), controller.remove(request.getId()));
				break;
			case "search_title":
				System.out.println("[Server] Search Title command");
				returnRes = new Response(request.getCommand(), controller.searchTitles(request.getSearchPattern()));
				break;
			case "search_artist":
				System.out.println("[Server] Search Artist command");
				returnRes = new Response(request.getCommand(), controller.searchArtist(request.getSearchPattern()));
				break;
			case "search_lyrics":
				System.out.println("[Server] Search Lyrics command");
				returnRes = new Response(request.getCommand(), controller.searchLyrics(request.getSearchPattern()));
				break;
			default:
				System.out.println("[Server] Unfamiliar command");
				break;
		}
		
		return returnRes;
	}
	
	public String responseToStr(Response response)
	{
		String responseAsString = "";
		
		JsonSerializer<Response> serializer = new JsonSerializer<Response>()
				{

					@Override
					public JsonElement serialize(Response arg0, Type arg1, JsonSerializationContext arg2) 
					{
						JsonObject response = new JsonObject();
						JsonObject action = new JsonObject();
						JsonObject body = new JsonObject();
						
						action.addProperty("action", arg0.getAction());
						body.addProperty("status", arg0.getStatus());
						
						JsonArray songListResponse = new JsonArray();
						List<Song> songList = arg0.getSongList();
						if (songList != null)
						{
							//add each song in the song list here:
							for (int i =0 ; i<songList.size() ; i++)
							{
								String title = songList.get(i).getTitle();
								String artist = songList.get(i).getArtist();
								String lyrics = songList.get(i).getSongLyrics();
								int songID = songList.get(i).getSongID();
								
								JsonObject song = new JsonObject();
								song.addProperty("title", title);
								song.addProperty("artist", artist);
								song.addProperty("lyrics", lyrics);
								song.addProperty("songID", songID);

								songListResponse.add(song);
							}
							
							body.add("songList", songListResponse);
						}
						else
						{
							body.add("songList", songListResponse); // empty jsonArray
						}
						
						response.add("headers", action);
						response.add("body", body);
						
						return response;
					}
			
				};
		
		GsonBuilder gson = new GsonBuilder();
		gson.registerTypeAdapter(Response.class, serializer);
		Gson gsonRep = gson.create();
		responseAsString = gsonRep.toJson(response);
				
		return responseAsString;
	}
	
	@Override
	public void run() 
	{
		System.out.println("Server - Handle Request now");
		String str = "";
		
			try {
			System.out.println(">> entered while");
			BufferedReader input = new BufferedReader(new InputStreamReader(this.mySocket.getInputStream()));
			PrintWriter output = new PrintWriter(this.mySocket.getOutputStream());
			
			str = input.readLine(); // read request str data
			System.out.println(">> request data read from user"+str);
			
			Request clientReq = parseRequest(str);
			// pass to controller here:
			// pass the answer to function that returns som str to go back to the client.
			Response serverResponse = requestControllerResponse(clientReq);
			String responseBack = responseToStr(serverResponse);

			System.out.println("[Server] Sending response back to client");
			output.write(responseBack);
			output.flush();
			
			System.out.println("[Server] Close buffers");
			output.close();
			input.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				mySocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}
	

		
}
