package optile.de.dropbox.list;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxEntry.WithChildren;
import com.google.inject.Inject;
import com.dropbox.core.DbxException;

import optile.de.dropbox.http.HttpClientDropbox;

public class DropboxListImpl implements DropboxList {
	private HttpClientDropbox httpDropboxService;
	
	@Inject
	public DropboxListImpl(HttpClientDropbox httpDropboxService) {
		this.httpDropboxService = httpDropboxService;
	}

	public static final String PATH_ARGUMENT_IS_MANDATORY = "Path argument is mandatory";
	public static final String ACCESS_TOKEN_IS_MANDATORY = "accessToken is mandatory";

	@Override
	public String list(DbxClient client,String accessToken, String path){
		if(path == null){
			return PATH_ARGUMENT_IS_MANDATORY;
		}else if(accessToken == null){
			return ACCESS_TOKEN_IS_MANDATORY;
		}

		
		StringBuilder message = new StringBuilder();
		DbxEntry entry = null;
		try {
			entry = client.getMetadata(path);
		} catch (DbxException e) {
			e.printStackTrace();
		}
		if (entry == null) {
			message.append("No file or folder at that path.");
		} else {
			if (entry.isFolder()){
				printFolderContent(client, accessToken, path, message, entry);
			}else if(entry.isFile()){
				message.append(printFile(entry, accessToken, path));
			}
		}
		return message.toString();

	}

	private void printFolderContent(DbxClient client, String accessToken, String path, StringBuilder message,
			DbxEntry entry) {
		message.append(printDir(entry, accessToken, path));
		try {
			WithChildren metadataWithChildren = client.getMetadataWithChildren(path);
			for (DbxEntry entry1 : metadataWithChildren.children) {
				if(entry1.isFile()){
					message.append(printFile(entry1, accessToken, path));					
				}else{
					message.append(printDir(entry, accessToken, path));
				}
			}
		} catch (DbxException e) {
			e.printStackTrace();
		}
	}

	private String printFile(DbxEntry entry, String accessToken, String path) {

		return "/" + entry.name + " : file, " + entry.asFile().humanSize + ", " +  
				httpDropboxService.extractMimeTypeForFile(accessToken, path) +", modified at:" + entry.asFile().lastModified + System.lineSeparator();
	}

	private String printDir(DbxEntry entry, String accessToken, String path) {
		return entry.path + " : dir, " + " modified at:"  + httpDropboxService.extractModifiedForFolder(accessToken, path) + System.lineSeparator();
	}

}
