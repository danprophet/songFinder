package com.hit.service;

import java.util.ArrayList;
import java.util.List;

import com.hit.algorithm.BoyerMooreAlgoImpl;
import com.hit.dao.DaoSongImpl;
import com.hit.dm.Song;

public class SearchSongTitlesService {

	private DaoSongImpl myDao = new DaoSongImpl("src\\main\\resources\\songsLyricsDB.json");
	private BoyerMooreAlgoImpl stringSearcher = new BoyerMooreAlgoImpl();

	public List<Song> getTitleSongList(String title) {
//		getClass().getResource(./)
		List<Song> songList = myDao.Read();
		List<Song> titleSongList = new ArrayList<Song>(); // song list of the titles found
		
		for (int songIndex=0 ; songIndex < songList.size() ; songIndex++)
		{
			int titlePos = this.stringSearcher.compareStrings(songList.get(songIndex).getTitle().toLowerCase(), title.toLowerCase());
			if (titlePos >= 0 ) // title exist
			{
				Song addMe = new Song(songList.get(songIndex));
				titleSongList.add(addMe);
			}
		}
		
		
		return titleSongList;
	}
	
	public List<Song> getAllSongsInDB() {
		List<Song> songList = myDao.Read();
		return songList;
	}

}
