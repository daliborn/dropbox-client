package optile.de.dropbox.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.google.inject.Inject;

import optile.de.dropbox.DropBoxService;

public class DropboxAuthenthicationImpl implements DropboxAuthentication {

	public static final String APP_SECRET_IS_NULL = "appSecret is null";
	public static final String APP_KEY_IS_NULL = "appKey is null";
	
	private BufferedReader reader;

	@Inject
	public DropboxAuthenthicationImpl(BufferedReader reader) {
		super();
		this.reader = reader;
	}

	public String authenticateUser(String appKey, String appSecret) {
		if (appKey == null) {
			return APP_KEY_IS_NULL;
		} else if (appSecret == null) {
			return APP_SECRET_IS_NULL;
		}

		DbxAppInfo appInfo = new DbxAppInfo(appKey, appSecret);
		DbxRequestConfig config = new DbxRequestConfig(DropBoxService.CLIENT_IDENTIFIER,
				Locale.getDefault().toString());
		DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

		printMessage(webAuth);
		String accessToken = extractToken(webAuth);
		return "token is verified! " + accessToken;

	}

	private String extractToken(DbxWebAuthNoRedirect webAuth) {
		String code = null;
		try {
			code = reader.readLine().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String accessToken = null;
		
		try {
			DbxAuthFinish authFinish = webAuth.finish(code);
			accessToken = authFinish.accessToken;
			
		} catch (DbxException e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	private void printMessage(DbxWebAuthNoRedirect webAuth) {
		String authorizeUrl = webAuth.start();
		System.out.println("1. Go to: " + authorizeUrl);
		System.out.println("2. Click \"Allow\" (you might have to log in first)");
		System.out.println("3. Copy the authorization code.");
	}
}
