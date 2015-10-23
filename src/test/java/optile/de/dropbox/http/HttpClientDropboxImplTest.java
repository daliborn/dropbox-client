package optile.de.dropbox.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.inject.Inject;

/**
 * @author dalibor
 * can't be tested currently because of third party libs.
 */
@RunWith(JukitoRunner.class)
public class HttpClientDropboxImplTest {
	private HttpClientDropboxImpl test;
	private String accessToken;
	private String path;
	@Inject
	private CloseableHttpClient client;
	private InputStream mockedStream;
	private HttpEntity mockedEntity;
	private CloseableHttpResponse mockedResponse;
	
	

	@Before
	public void setUp() throws Exception {
		mockedResponse = Mockito.mock(CloseableHttpResponse.class);
		mockedEntity = Mockito.mock(HttpEntity.class);
		mockedStream= Mockito.mock(InputStream.class);

		when(client.execute((HttpGet) any())).thenReturn(mockedResponse );
		when(mockedResponse.getEntity()).thenReturn(mockedEntity);
		when(mockedEntity.getContent()).thenReturn(mockedStream);
		test = new HttpClientDropboxImpl(client);
	}

	@Test 
	public void testExtractMimeTypeForFile() throws IOException {
		when(mockedStream.read()).thenReturn(123);
		String result = test.extractMimeTypeForFile(accessToken, path);
		assertNull(result);
	}

	@Test
	public void testExtractModifiedForFolder() {
		String result = test.extractModifiedForFolder(accessToken, path);
		assertNull(result);
	}
	
	//TODO: add more test's after adding third party mock support  

}
