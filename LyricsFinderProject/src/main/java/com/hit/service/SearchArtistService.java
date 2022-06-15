package com.hit.service;
import java.util.ArrayList;
import java.util.List;

import com.hit.algorithm.NaiveAlgoImpl;
import com.hit.dao.DaoSongImpl;
import com.hit.dm.Song;

public class SearchArtistService {

	private DaoSongImpl myDao = new DaoSongImpl("src\\main\\resources\\songsLyricsDB.json");
	private NaiveAlgoImpl stringSearcher = new NaiveAlgoImpl();

	public List<Song> getArtistSongList(String artist) {

		List<Song> songList = myDao.Read();
		List<Song> artistsSongList = new ArrayList<Song>(); // song list of the artist that chosen
		
		for (int songIndex=0 ; songIndex < songList.size() ; songIndex++)
		{
			int artistExists = this.stringSearcher.compareStrings(songList.get(songIndex).getArtist(), artist);
			if (artistExists >= 0 ) // artist exists
			{
				Song addMe = new Song(songList.get(songIndex));
				artistsSongList.add(addMe);
			}
		}
		
		return artistsSongList;
	}

}
