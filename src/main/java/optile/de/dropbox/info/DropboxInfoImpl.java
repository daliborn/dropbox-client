package optile.de.dropbox.info;

import com.dropbox.core.DbxAccountInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;

public class DropboxInfoImpl implements DropboxInfo {

	
	@Override
	public String info(DbxClient client) {


		try {
			return printReport(client);
		} catch (DbxException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	private String printReport(DbxClient client) throws DbxException{
		StringBuilder sb = new StringBuilder();
		DbxAccountInfo account = client.getAccountInfo();
		sb.append("--------------------------------------------------------" + System.lineSeparator());
		sb.append("User ID: " + account.userId + System.lineSeparator());
		sb.append("Display name: " + account.displayName + System.lineSeparator());
		sb.append("Name: " + account.nameDetails.givenName +" " + 
				account.nameDetails.surname + " (" + account.nameDetails.familiarName + ")" + System.lineSeparator());
		sb.append("E-mail: " + account.email);
		
		if(account.emailVerified == true){
			sb.append(" (verified)");			
		}
		sb.append(System.lineSeparator());
		sb.append("Country: " + account.country+ System.lineSeparator());
		sb.append("Referral link: " + account.referralLink+ System.lineSeparator());
		sb.append("--------------------------------------------------------");
		return sb.toString();
	}
}
