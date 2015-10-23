package optile.de.dropbox;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.dropbox.core.DbxAppInfo;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import optile.de.dropbox.auth.DropboxAuthenthicationImpl;
import optile.de.dropbox.auth.DropboxAuthentication;
import optile.de.dropbox.http.HttpClientDropbox;
import optile.de.dropbox.http.HttpClientDropboxImpl;
import optile.de.dropbox.info.DropboxInfo;
import optile.de.dropbox.info.DropboxInfoImpl;
import optile.de.dropbox.list.DropboxList;
import optile.de.dropbox.list.DropboxListImpl;

/**
 * @author dalibor
 * configurations mapping for Dependency injections
 *
 */
public class DropBoxModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(DropboxAuthentication.class).to(DropboxAuthenthicationImpl.class);
		bind(DropboxInfo.class).to(DropboxInfoImpl.class);
		bind(DropboxList.class).to(DropboxListImpl.class);
		bind(HttpClientDropbox.class).to(HttpClientDropboxImpl.class);

	}

	@Provides
	DbxAppInfo provideTransactionLog(String appKey, String appSecret) {
		DbxAppInfo appInfo = new DbxAppInfo(appKey, appSecret);
		return appInfo;
	}
	
	@Provides
	BufferedReader provideConsoleReader() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return reader;
	}
	
	@Provides
	CloseableHttpClient provideHttpclient() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		return httpclient;
	}


}
