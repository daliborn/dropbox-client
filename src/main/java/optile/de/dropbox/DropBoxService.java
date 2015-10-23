package optile.de.dropbox;

import java.util.Locale;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxRequestConfig;
import com.google.inject.Inject;

import optile.de.dropbox.auth.DropboxAuthentication;
import optile.de.dropbox.info.DropboxInfo;
import optile.de.dropbox.list.DropboxList;

/**
 * @author dalibor Smaller units of works are injected here and validation tasks
 *         for input parameters
 */
public class DropBoxService {
	private final DropboxAuthentication auth;
	private final DropboxInfo info;
	private final DropboxList list;

	public static final String CLIENT_IDENTIFIER = "dropbox-daliborn";

	@Inject
	DropBoxService(DropboxAuthentication auth, DropboxInfo info, DropboxList list) {
		this.auth = auth;
		this.info = info;
		this.list = list;

	}

	public void auth(String appKey, String appSecret) {
		System.out.print(auth.authenticateUser(appKey, appSecret));
	}

	public void info(String accessToken, String locale) {
		if (accessToken == null) {
			System.out.print("accessToken is mandatory");
			return;
		}
		DbxClient client = initilazeClient(accessToken, locale);

		System.out.print(info.info(client));

	}

	public void list(String accessToken, String path, String locale) {
		if (accessToken == null) {
			System.out.print("accessToken is mandatory");
			return;
		}
		DbxClient client = initilazeClient(accessToken, locale);
		System.out.println(list.list(client, accessToken, path));

	}

	private DbxClient initilazeClient(String accessToken, String locale) {

		if (locale == null) {
			locale = Locale.getDefault().toString();
		}
		DbxRequestConfig config = new DbxRequestConfig(DropBoxService.CLIENT_IDENTIFIER, locale);

		DbxClient client = new DbxClient(config, accessToken);
		return client;
	}

	public void start(String[] args) {
		if (args.length < 1) {
			System.out
					.print("application should have at least two arguments! Available comands (M - mandatory):\nauth {appKey}M {appSecret}M"
							+ "\ninfo {accessToken}M {locale}\nlist {accessToken}M {path}M {locale}");
			return;
		}
		callServiceWithCommand(args);
	}

	private void callServiceWithCommand(String[] args) {
		String command = args[0];

		switch (command) {
		case "auth":
			auth(args[1], args[2]);
			break;
		case "info":
			if (args.length > 3) {
				info(args[1], args[2]);
			} else {
				info(args[1], null);
			}
			break;
		case "list":
			if (args.length > 3) {
				list(args[1], args[2], args[3]);
			} else {
				list(args[1], args[2], null);
			}
			break;
		default:
			System.out.print("Available comands: auth, info, list");
			break;

		}
	}
}
