package com.hit.service;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.hit.dm.Song;
import com.hit.util.*; 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class AddSongServiceTest {
	private AddSongService myService = new AddSongService();
	private SearchSongTitlesService search = new SearchSongTitlesService();
	private SearchArtistService searchArtist = new SearchArtistService();
	private SearchLyricsService searchLyrics = new SearchLyricsService();
	private RemoveSongService removeSongService = new RemoveSongService();
	private String songName = "Test Song";
	private String artistName = "Test Artist";
	private String lyrics = "Test Lyrics";
	
	@Test // Add new Song
	public void AddSongServiceTest1() 
	{
		boolean result = myService.addSongToDB("Test Song", "Test Artist", "Test Lyrics");
		assertEquals(true, result);
	}
	
//	@Test // test BackupAndRestoreTest
//	public void AddSongServiceTest2()
//	{
//		// naiveAddSongTest() will be executed firstly (due to use of @FixMethodOrder) and the new song "Test Song" is added to the original DB
//		BackupAndRestore backupTest = new BackupAndRestore();
//		String originalDBPath = "src\\main\\resources\\songsLyricsDB.json";
//		String backPath = "src\\main\\resources\\songsLyricsDB_backup.json";
//		int delay = 1;
//		int period = 1;
//		
//		backupTest.backup(originalDBPath, backPath, delay, period);
//		List<Song> backupDB = (List<Song>) backupTest.restore(backPath);
//		
//		boolean songFoundInCopy = false;
//		
//		for (int songIndex = 0 ; songIndex<backupDB.size() ; songIndex++)
//		{
//			String currentSongLyrics = backupDB.get(songIndex).getSongLyrics();
//			if(currentSongLyrics.equalsIgnoreCase(this.lyrics))
//			{
//				songFoundInCopy=true;
//				break;
//			}
//		}
//		
//		assertEquals(true, songFoundInCopy);
//	}
	
	@Test // test added title
	public void AddSongServiceTest3() 
	{
		List<Song> result = search.getTitleSongList(this.songName);
		boolean compareResult = false;
		String returnedSongTitle = "";
		
		for (int i = 0 ; i<result.size() ; i++)
		{
			compareResult = result.get(i).getTitle().equals(this.songName);
			if (compareResult)
			{
				returnedSongTitle = result.get(i).getTitle();
				break;
			}
		}
		assertEquals(this.songName, returnedSongTitle);
	}
	
	@Test // test added artist
	public void AddSongServiceTest4() 
	{
		List<Song> result = searchArtist.getArtistSongList(this.artistName);
		boolean compareResult = false;
		String returnedSongArtist = "";
		
		for (int i = 0 ; i<result.size() ; i++)
		{
			compareResult = result.get(i).getArtist().equals(this.artistName);
			if (compareResult)
			{
				returnedSongArtist = result.get(i).getArtist();
				break;
			}
		}
		
		assertEquals(this.artistName, returnedSongArtist);
	}
	
	@Test // test added lyrics
	public void AddSongServiceTest5() 
	{
		List<Song> result = searchLyrics.getSong(this.lyrics);
		assertEquals(this.lyrics, result.get(0).getSongLyrics());
	}
	
	@Test // remove added song from the DB
	public void AddSongServiceTest6() 
	{
		List<Song> result = searchLyrics.getSong("Test Lyrics");
		boolean removeResult = removeSongService.removeSongFromList(result.get(0).getSongID());
		assertEquals(true, removeResult);
	}
	
}
