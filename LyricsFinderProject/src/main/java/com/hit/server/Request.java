package com.hit.server;

public class Request {
	
	private String command = "";
	private String title = "";
	private String artist = "";
	private String lyrics = "";
	private String search = "";
	int id = -1;
	
	public Request(String command, String title, String artist, String lyrics) // Add Ctor
	{
		this.command = command;
		this.title = title;
		this.artist = artist;
		this.lyrics = lyrics;
	}
	
	public Request(String command, String search) // Search Ctor
	{
		this.command = command;
		this.search = search;
	}
	
	public Request(String command, int id)
	{
		this.command = command;
		this.id = id;
	}
	
	public Request(String command)
	{
		this.command = command;
	}
	
	public String getCommand()
	{
		return this.command;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getArtist()
	{
		return this.artist;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getLyrics()
	{
		return this.lyrics;
	}
	
	public String getSearchPattern()
	{
		return this.search;
	}
	
}
