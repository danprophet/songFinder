package com.hit.util;
import java.util.TimerTask;
import com.hit.dao.DaoSongImpl;
import com.hit.dm.Song;
import java.util.List;
import java.util.Timer;

 public class BackupAndRestore {
	public void backup(String fromFilePath, String toPathBackup, long delay, long period) 
	{
			DaoSongImpl originalDBDao = new DaoSongImpl(fromFilePath); // "src\\main\\resources\\songsLyricsDB.json"
			DaoSongImpl backDBDao = new DaoSongImpl(toPathBackup);  // "src\\main\\resources\\songsLyricsDB_backup.json"
			
			TimerTask backupTask = new TimerTask() {
			public void run() 
				{
					List<Song> originalDB = originalDBDao.Read();
					
					for (int songIndex=0 ; songIndex<originalDB.size() ; songIndex++)
					{
						Song currentSong = originalDB.get(songIndex);
						backDBDao.Write(currentSong);
					}
				}
			};
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(backupTask, delay, period);
	}
	
	public Object restore(String fromFilePath) {
		DaoSongImpl backDBDao = new DaoSongImpl(fromFilePath);
		List<Song> myBackupSongList = backDBDao.Read();
		return myBackupSongList;
		}

}