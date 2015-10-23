package optile.de.dropbox.integration;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import optile.de.dropbox.Main;

@Ignore
public class MainTest {
	//used for console output testing
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	
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
	public void testMainShouldReturnValidResult() {
		//IMPORTANT: sensitive data used on purpose
		String[] args = new String[]{"auth", "dngveq8kufelfcm", "7w8xut73ut5qs22"};
		Main.main(args);
		assertEquals(mockedResponse(), outContent.toString());
		//no error logs
		assertEquals("",errContent.toString());		
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
