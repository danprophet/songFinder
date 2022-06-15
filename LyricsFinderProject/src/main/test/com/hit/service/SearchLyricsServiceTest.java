package com.hit.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.hit.dm.Song;

public class SearchLyricsServiceTest {
	SearchLyricsService myService = new SearchLyricsService();
	AddSongService addSongServ = new AddSongService();
	RemoveSongService removeSongServ = new RemoveSongService();
	
	@Test
	public void findSongLyricsTest()
	{
		String title = "abcd";
		String artist = "artist123";
		String lyrics = "Those are my unique lyrics";
		boolean result = false;
		
		boolean resultAdding = addSongServ.addSongToDB(title, artist, lyrics);
		if (resultAdding)
		{
			List<Song> songsFound = myService.getSong(lyrics);
			for (int i =0 ; i<songsFound.size() ; i++)
			{
				if (songsFound.get(i).getSongLyrics().equalsIgnoreCase(lyrics))
				{
					result=true;
					removeSongServ.removeSongFromList(songsFound.get(i).getSongID());
					break;
				}
			}

		}
		assertEquals(true, result);
	}
}
