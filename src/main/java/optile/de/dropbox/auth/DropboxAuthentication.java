package optile.de.dropbox.auth;

public interface DropboxAuthentication {
	String authenticateUser(String appKey, String appSecret);
}
