package com.hit.dao;
import com.hit.dm.*;
import com.google.gson.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class DaoSongImpl implements IDao{
	public Song resultSong; // song object to be returned to the caller of the DAO
	private String filePath = ""; // filePath of JSON file with song list
	
	public DaoSongImpl(String filePath)
	{
		this.filePath = filePath;
	}
	
	public List<Song> Read()
	{
		List<Song> songList = new ArrayList<Song>();
		Gson gson = new Gson();
		try {
			JsonObject songsKey = gson.fromJson(new FileReader(this.filePath), JsonObject.class);
			JsonArray  songsJasonArray = songsKey.getAsJsonArray("songs");
			
			for (int songIndex=0 ; songIndex < songsJasonArray.size() ; songIndex++)
			{
				JsonElement songElement = songsJasonArray.get(songIndex);
				Song song = gson.fromJson(songElement, Song.class); 
				songList.add(song);
			}

		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return songList;
	}
	
	public boolean Write(Song newSong)
	{
		// Attempt to write/add song
		Gson gson = new Gson();
		JsonObject jsonFile;
		JsonObject newFile = new JsonObject();
		
		try {
			jsonFile = gson.fromJson(new FileReader(filePath), JsonObject.class);
			JsonArray  jsonArraySongs = jsonFile.getAsJsonArray("songs");
			JsonElement newSongJsonElement = gson.toJsonTree(newSong);
			jsonArraySongs.add(newSongJsonElement);
			newFile.add("songs", jsonArraySongs);
			
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		
		FileWriter writer;
		try {
			writer = new FileWriter(this.filePath);
			String writeJsonInfo = newFile.toString();
			
			writer.write(writeJsonInfo);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean Remove(int songID)
	{
		JsonObject newFile = new JsonObject();
		boolean foundSong = false;
		Gson gson = new Gson();
		try {
			JsonObject songsKey = gson.fromJson(new FileReader(this.filePath), JsonObject.class);
			JsonArray  songsJasonArray = songsKey.getAsJsonArray("songs");
			
			for (int songIndex=0 ; songIndex < songsJasonArray.size() ; songIndex++)
			{
				JsonElement songElement = songsJasonArray.get(songIndex);
				Song song = gson.fromJson(songElement, Song.class); 
				if (song.getSongID() == songID)
				{
					songsJasonArray.remove(songIndex); // remove song from current songList in server
					foundSong = true;
					break;
				}
			}	
			
			if (foundSong) // only if song ID exists, update song list DB
			{
				// update list in file
				newFile.add("songs", songsJasonArray);
				FileWriter writer;
				try {
					writer = new FileWriter(this.filePath);
					String writeJsonInfo = newFile.toString();
					
					writer.write(writeJsonInfo);
					writer.flush();
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
				return true;
			}
			return false;

		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}

}
