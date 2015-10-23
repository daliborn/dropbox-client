package optile.de.dropbox;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jukito.JukitoRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.dropbox.core.DbxClient;
import com.google.inject.Inject;

import optile.de.dropbox.auth.DropboxAuthentication;
import optile.de.dropbox.info.DropboxInfo;
import optile.de.dropbox.list.DropboxList;
import static org.mockito.Matchers.*;

@RunWith(JukitoRunner.class)
public class DropBoxServiceTest {
	private static final String appKey = "123456";
	private static final String appSecret = "abcdef";
	private DropBoxService test;
	@Inject
	private DropboxAuthentication auth;
	@Inject
	private DropboxInfo info;
	@Inject
	private DropboxList list;
	
	//used for console output testing
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUp() throws Exception {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		test = new DropBoxService(auth, info, list);
	}


	@Test
	public void testAuth() {
		when(auth.authenticateUser(anyString(), anyString())).thenReturn("test");
		test.auth(appKey, appSecret);
		assertEquals("test", outContent.toString());
	}

	@Test
	public void testInfoShoulReturnValidResult() {
		when(info.info((DbxClient) any())).thenReturn("test");
		String accessToken = "123456";
		String locale = "US";
		test.info(accessToken, locale);
		assertEquals("test", outContent.toString());
	}
	
	@Test
	public void testInfoLocaleEmptyShoulReturnErrorResult() {
		when(info.info((DbxClient) any())).thenReturn("test");
		String accessToken = "123";
		String locale = null;
		test.info(accessToken, locale);
		assertEquals("test", outContent.toString());
	}
	
	@Test
	public void testInfoTokenEmptyShoulReturnErrorResult() {
		test.info(null, null);
		assertEquals("accessToken is mandatory", outContent.toString());
	}
	
	//FIXME: implement mock of final classes
	@Ignore
	@Test
	public void testList() {
		DbxClient client = Mockito.mock(DbxClient.class);
		String accessToken = null;
		String path = null;
		String locale = null;
		when(list.list(client, accessToken, path)).thenReturn("test");
		test.list(accessToken, path, locale);
		
		assertEquals("test", outContent.toString());
	}

	@Test
	public void testStartNoArgumentsShouldReturnError() {
		String[] args = new String[]{};
		test.start(args );
		assertEquals(mockedResponse(), outContent.toString());
	}
	
	@Test
	public void testStartWrongArgumentsShouldReturnError() {
		String[] args = new String[]{"wrong","command"};
		test.start(args );
		assertEquals("Available comands: auth, info, list", outContent.toString());
	}
	
	private String mockedResponse() {
		return "application should have at least two arguments! Available comands (M - mandatory):\nauth {appKey}M {appSecret}M"
				+ "\ninfo {accessToken}M {locale}\nlist {accessToken}M {path}M {locale}";
	}


	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}

}
