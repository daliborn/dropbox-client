package optile.de.dropbox.list;

import com.dropbox.core.DbxClient;

public interface DropboxList {
	String list(DbxClient client, String accessToken, String path);
}
