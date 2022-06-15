/* Save or edit DM */
package com.hit.service;
import java.util.List;

import com.hit.dao.*;
import com.hit.dm.Song;

public class AddSongService {
	private DaoSongImpl myDao = new DaoSongImpl("src\\main\\resources\\songsLyricsDB.json");
	
	public boolean addSongToDB(String title, String artist, String songLyrics)
	{
		List<Song> mySongList = myDao.Read();
		Song.updateMaxIDFromExistingDB(mySongList);
		
		boolean addSongResult = myDao.Write(new Song(title, artist, songLyrics));
		return addSongResult;
	}
	
	public boolean addSongToDB(Song song)
	{
		return this.addSongToDB(song.getTitle(), song.getArtist(), song.getSongLyrics());
	}
	
}
