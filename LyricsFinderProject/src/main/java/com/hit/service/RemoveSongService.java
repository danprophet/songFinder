package com.hit.service;
import com.hit.dao.*;

public class RemoveSongService {
	private DaoSongImpl myDao = new DaoSongImpl("src\\main\\resources\\songsLyricsDB.json");
	
	public boolean removeSongFromList(int SongID)
	{
		boolean result = myDao.Remove(SongID);
		return result;
	}

}
