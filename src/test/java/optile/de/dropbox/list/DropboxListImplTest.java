package optile.de.dropbox.list;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxEntry.WithChildren;
import com.dropbox.core.DbxException;

import optile.de.dropbox.http.HttpClientDropbox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DbxClient.class,WithChildren.class})
public class DropboxListImplTest{
	private DropboxListImpl test;


	@Test
	public void testListPathIsNullShuldReturnErrorMessage() {
		HttpClientDropbox httpDropboxService = PowerMockito.mock(HttpClientDropbox.class);
		test = new DropboxListImpl(httpDropboxService);
		DbxClient client = PowerMockito.mock(DbxClient.class);
		String path = null;
		String result = test.list(client, path, null);
		assertEquals(DropboxListImpl.PATH_ARGUMENT_IS_MANDATORY ,result);
	}
	
	@Test
	public void testListTokenIsEmptyShuldReturnError() throws DbxException {
		HttpClientDropbox httpDropboxService = PowerMockito.mock(HttpClientDropbox.class);	
		test = new DropboxListImpl(httpDropboxService);


		String path = "/akka";
		
		DbxClient client = PowerMockito.mock(DbxClient.class);
		String result = test.list(client, null, path);
		
		assertEquals(DropboxListImpl.ACCESS_TOKEN_IS_MANDATORY,result);

	}
	
	@Test
	public void testListShuldReturnValidResponse() throws DbxException {
		String path = "/akka";
		HttpClientDropbox httpDropboxService = PowerMockito.mock(HttpClientDropbox.class);	
		DbxClient client = PowerMockito.mock(DbxClient.class);		
		DbxEntry entry = new DbxEntry.Folder(path, "icon name", false);
		List<DbxEntry> children = new ArrayList<>();
		Date lastModified = new Date(1445455699000L);
		DbxEntry entryFromList = new DbxEntry.File (path + "/file.txt", "icon name", false, 100, "1 MB", lastModified , null, "123");
		children.add(entryFromList );
		String hash = "123456";
		WithChildren metadataWithChildren = new WithChildren(entryFromList, hash, children);
		
		
		test = new DropboxListImpl(httpDropboxService);
		
		Mockito.when(client.getMetadata(path)).thenReturn(entry);
		Mockito.when(client.getMetadataWithChildren(path)).thenReturn(metadataWithChildren);

		String token ="12345678910";
		String result = test.list(client, token , path);
		
		// Back to the Future
		assertEquals("/akka : dir,  modified at:null" + System.lineSeparator() + "/file.txt : file, 1 MB, null, modified at:Wed Oct 21 21:28:19 CEST 2015"+ System.lineSeparator() ,result);

	}
	
	@Test
	public void testListFileShuldReturnValidResponse() throws DbxException {
		String path = "/akka";
		HttpClientDropbox httpDropboxService = PowerMockito.mock(HttpClientDropbox.class);	
		DbxClient client = PowerMockito.mock(DbxClient.class);		
		Date lastModified = new Date(1445455699000L);
		DbxEntry entry = new DbxEntry.File (path + "/file.txt", "icon name", false, 100, "1 MB", lastModified , null, "123");	
		
		test = new DropboxListImpl(httpDropboxService);
		
		Mockito.when(client.getMetadata(path)).thenReturn(entry);


		String token ="12345678910";
		String result = test.list(client, token , path);
		
		// Back to the Future
		assertEquals("/file.txt : file, 1 MB, null, modified at:Wed Oct 21 21:28:19 CEST 2015" + System.lineSeparator(),result);

	}


}
