package optile.de.dropbox.integration;

import optile.de.dropbox.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;


public class MainTest {
	//used for console output testing
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();
	
	
	@Before
	public void setUp() throws Exception {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));		
	}

	@Test
	public void testMainNoArgListCommands() {
		String[] args = new String[]{};
		Main.main(args);
		assertEquals(mockedResponse(), outContent.toString());
		//no error logs
		assertEquals("",errContent.toString());		
	}
	
	@Test
	public void testMainShouldReturnValidResult() throws IOException {
        String resourceName = "config.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            props.load(resourceStream);
        }


        systemInMock.provideText(props.getProperty("token"));
		String[] args = new String[]{"auth", props.getProperty("appkey"), props.getProperty("appsecret")};
		Main.main(args);
		assertEquals(validAuthResponse(), outContent.toString());
		//no error logs
		assertEquals("",errContent.toString());		
	}

    private String validAuthResponse(){
       return "1. Go to: https://www.dropbox.com/1/oauth2/authorize?locale=en_US&client_id=dngveq8kufelfcm&response_type=code\n" +
        "2. Click \"Allow\" (you might have to log in first)\n" +
        "3. Copy the authorization code.\n" +
        "token is verified! null";
    }
	
	private String mockedResponse() {
		return "application should have at least two arguments! Available comands (M - mandatory):\nauth {appKey}M {appSecret}M"
				+ "\ninfo {accessToken}M {locale}\nlist {accessToken}M {path}M {locale}";
	}
	
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}

}
