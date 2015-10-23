package optile.de.dropbox.http;

public interface HttpClientDropbox {
	public String extractMimeTypeForFile(String accessToken, String path);

	public String extractModifiedForFolder(String accessToken, String path);
}
