package optile.de.dropbox.info;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.dropbox.core.DbxAccountInfo;
import com.dropbox.core.DbxAccountInfo.NameDetails;
import com.dropbox.core.DbxAccountInfo.Quota;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;

/**
 * @author dalibor
 * Powermock runner is used because DbxClient is final class 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(DbxClient.class)
public class DropboxInfoImplTest {
	private DropboxInfoImpl test;
	private DbxAccountInfo account;


	@Before
	public void setUp() throws Exception {
		test = new DropboxInfoImpl();
		long userId = 0;
		String displayName = null;
		String country = null;
		String referralLink = null;
		Quota quota = null;
		String email = null;
		String familiarName = null;
		String givenName = null;
		String surname = null;
		NameDetails nameDetails = new NameDetails(familiarName, givenName, surname);
		boolean emailVerified = false;
		account = new DbxAccountInfo(userId, displayName, country, referralLink, quota, email, nameDetails, emailVerified);
	}

	@Test
	public void testInfoString() throws DbxException {
		DbxClient client = PowerMockito.mock(DbxClient.class);
		Mockito.when(client.getAccountInfo()).thenReturn(account);
		String result = test.info(client);
		
		assertEquals(mockedResult() ,result);
	}
	
	private String mockedResult(){
		StringBuilder sb = new StringBuilder();
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
