package optile.de.dropbox.auth;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(JukitoRunner.class)
public class DropboxAuthenthicationImplTest {
	private static final String appKey = "123";
	private static final String appSecret = "abc";
	private DropboxAuthenthicationImpl test;
	@Inject
	private BufferedReader reader;

	@Before
	public void setUp() throws Exception {
		when(reader.readLine()).thenReturn("first line");
		test = new DropboxAuthenthicationImpl(reader);
	}

	@Test
	public void testAuthenticateUserAppKeyIsNullShouldReturnErrormessage() {
		String result = test.authenticateUser(null, appSecret);
		assertEquals(DropboxAuthenthicationImpl.APP_KEY_IS_NULL, result);
	}
	
	@Test
	public void testAuthenticateUserAppSecretIsNullShouldReturnErrormessage() {
		String result = test.authenticateUser(appKey, null);
		assertEquals(DropboxAuthenthicationImpl.APP_SECRET_IS_NULL, result);
	}
	
	@Test
	public void testAuthenticateUserShouldReturnValidMessage() throws IOException {		
		String result = test.authenticateUser(appKey, appSecret);
		assertEquals("token is verified! null", result);
	}

}
