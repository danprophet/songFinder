package com.hit.controller;
import java.util.List;

import com.hit.dm.Song;
import com.hit.service.AddSongService;
import com.hit.service.RemoveSongService;
import com.hit.service.SearchArtistService;
import com.hit.service.SearchLyricsService;
import com.hit.service.SearchSongTitlesService;

public class Controller {
	AddSongService add;
	RemoveSongService remove;
	SearchArtistService searchArtist;
	SearchLyricsService searchLyrics;
	SearchSongTitlesService searchTitles;
	
	public Controller()
	{
		add = new AddSongService();
		remove = new RemoveSongService();
		searchArtist = new SearchArtistService();
		searchLyrics = new SearchLyricsService();
		searchTitles = new SearchSongTitlesService();
	}
	
	public boolean add(Song newSong)
	{
		return add.addSongToDB(newSong);
	}
	
	public boolean remove (int songId)
	{
		return remove.removeSongFromList(songId);
	}
	
	public List<Song> searchArtist(String artist)
	{
		return searchArtist.getArtistSongList(artist);
	}
	
	public List<Song> searchLyrics(String lyrics)
	{
		return searchLyrics.getSong(lyrics);
	}
	
	public List<Song> searchTitles(String title)
	{
		return searchTitles.getTitleSongList(title);
	}
	
	public List<Song> getAllDB()
	{
		return searchTitles.getAllSongsInDB();
	}
	
	

}
