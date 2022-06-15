package com.hit.dao;
import com.hit.dm.Song;

public interface IDao {
	public Object Read();
	public boolean Write(Song newSong);
	public boolean Remove(int songID);
}
