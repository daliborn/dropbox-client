package optile.de.dropbox.integration;

import optile.de.dropbox.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
	public void testMainShouldReturnValidResult() {
		//IMPORTANT: sensitive data used on purpose
        systemInMock.provideText("1\n2\n");
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
