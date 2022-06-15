package com.hit.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.hit.dm.Song;


public class RemoveSongServiceTest {
	private AddSongService addService = new AddSongService();
	private RemoveSongService myService = new RemoveSongService();
	private SearchLyricsService lyricsService = new SearchLyricsService();

	@Test
	public void removeSongTest()
	{
	String lyrics = "New Lyrics123";
	boolean success = false;
	boolean addResult = addService.addSongToDB("New Title", "New Artist", lyrics);
	
	if (addResult) 
	{
		List<Song> mySong = lyricsService.getSong(lyrics);
		boolean removeResult = myService.removeSongFromList(mySong.get(0).getSongID());
		
		if (removeResult)
		{
			List<Song> songSearchAfterRemove = lyricsService.getSong(lyrics);
			for (int i=0 ; i<songSearchAfterRemove.size() ; i++)
			{
				if (songSearchAfterRemove.get(i).getTitle().equalsIgnoreCase("Empty Song"))
				{
					success = true;
					break;
				}

			}

		}
		assertEquals(true, success);
	}
	
	}
}
