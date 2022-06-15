package com.hit.service;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import com.hit.algorithm.KMPAlgoImpl;
import com.hit.dm.Song;

public class SearchArtistServiceTest {
	private SearchArtistService myService = new SearchArtistService();
	private KMPAlgoImpl stringMatch = new KMPAlgoImpl();
	
	@Test
	public void testArtistExists()
	{
		String searchMe = "Infected Mushroom";
		boolean exists = false;
		List<Song> result = myService.getArtistSongList(searchMe);
		for (int i =0 ; i<result.size() ; i++)
		{
			int position = stringMatch.compareStrings(searchMe,result.get(i).getArtist());
			if (position >= 0)
			{
				exists = true;
				break;
			}
		}
		assertEquals(true, exists);
	}
	
	@Test
	public void testArtistNotExists()
	{
		String searchMe = "Shlomo Artzi";
		boolean exists = false;
		List<Song> result = myService.getArtistSongList(searchMe);
		for (int i =0 ; i<result.size() ; i++)
		{
			int position = stringMatch.compareStrings(searchMe,result.get(i).getArtist());
			if (position >= 0)
			{
				exists = true;
				break;
			}
		}
		assertEquals(false, exists);
	}

}
