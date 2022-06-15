package com.hit.service;
import com.hit.dao.*;
import com.hit.dm.Song;

import java.util.ArrayList;
import java.util.List;
import com.hit.algorithm.*;

public class SearchLyricsService {
	private DaoSongImpl myDao = new DaoSongImpl("src\\main\\resources\\songsLyricsDB.json");
	private KMPAlgoImpl stringSearcher = new KMPAlgoImpl();

	public List<Song> getSong(String lyrics) {
		boolean songsFound = false; // identification in case songs exits
		List<Song> songList = myDao.Read();
		List<Song> returnSongList =  new ArrayList<Song>();
		
		for (int songIndex=0 ; songIndex < songList.size() ; songIndex++)
		{
			int lyricsPosition = this.stringSearcher.compareStrings(lyrics, songList.get(songIndex).getSongLyrics());
			if (lyricsPosition >= 0 ) // lyrics exist
			{
				returnSongList.add(songList.get(songIndex)); // return current song.
				
				if (!songsFound)
				{
					songsFound = true;
				}
			}
		}
		
		if (!songsFound)
		{
			returnSongList.add(new Song()); // empty Song gets back in case the song not found.
		}
		
		return returnSongList;
	}
	
}
