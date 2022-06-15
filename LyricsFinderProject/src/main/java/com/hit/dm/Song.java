package com.hit.dm;

import java.util.List;

public class Song {
	private static int id;
	private String title;
	private String artist;
	private String lyrics;
	private int songID;
	
	// Ctor's:
	public Song()
	{
		this.title = "Empty Song";
		this.artist = "Empty Song";
		this.lyrics = "Empty Song";
		this.songID = id+1;
		id++;
	}
	public Song(String title, String artist, String songLyrics)
	{
		this.title = title;
		this.artist = artist;
		this.lyrics = songLyrics;
		this.songID = id+1;
		id++;
	}
	
	//constructor when read from file
	public Song(String title, int songID, String artist, String songLyrics)
	{
		this.title = title;
		this.artist = artist;
		this.lyrics = songLyrics;
		this.songID = songID;
		
		if (songID > id) // save the unique id for each song
		{
			id = songID +1;
		}
	}
	
	// Deep Copy Ctor
	public Song(Song cpySong) {
		this.title = cpySong.getTitle();
		this.artist = cpySong.getArtist();
		this.lyrics = cpySong.getSongLyrics();
		this.songID = cpySong.getSongID();
	}
	
	public static int updateMaxIDFromExistingDB(List<Song> songList)
	{
		int newSetID = 0;
		if (songList.size() > 0)
		{
			for (int i=0 ; i<songList.size() ; i++)
			{
				if (newSetID < songList.get(i).getSongID())
				{
					newSetID = songList.get(i).getSongID();
				}
			}
		
			id = newSetID; // update only in case there is songlist existing.
		}
		
		return newSetID;
	}
	
	// Getters	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getArtist()
	{
		return this.artist;
	}
	
	public String getSongLyrics()
	{
		return this.lyrics;
	}
	
	public int getSongID()
	{
		return this.songID;
	}
	
	// Setters
	public void setTitle(String newTitle)
	{
		this.title = newTitle;
	}
	
	public void setArtist(String newArtist)
	{
		this.artist = newArtist;
	}

	
	public void setSongLyrics(String newSongLyrics)
	{
		this.lyrics = newSongLyrics;
	}
	


}
