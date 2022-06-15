package com.hit.service;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import com.hit.algorithm.KMPAlgoImpl;
import com.hit.dm.Song;

public class SearchSongTitlesServiceTest {

	private SearchSongTitlesService myService = new SearchSongTitlesService();
	private KMPAlgoImpl stringMatch = new KMPAlgoImpl();
	
	@Test
	public void testTitleExists()
	{
		String searchMe = "Do I Wanna Know";
		boolean exists = false;
		List<Song> result = myService.getTitleSongList(searchMe);
		for (int i =0 ; i<result.size() ; i++)
		{
			int position = stringMatch.compareStrings(searchMe,result.get(i).getTitle());
			if (position >= 0)
			{
				exists = true;
				break;
			}
		}
		assertEquals(true, exists);
	}
	
	@Test
	public void testTitleNotExists()
	{
		String searchMe = "Kashmir";
		boolean exists = false;
		List<Song> result = myService.getTitleSongList(searchMe);
		for (int i =0 ; i<result.size() ; i++)
		{
			int position = stringMatch.compareStrings(searchMe,result.get(i).getTitle());
			if (position >= 0)
			{
				exists = true;
				break;
			}
		}
		assertEquals(false, exists);
	}

}
