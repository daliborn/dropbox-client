package optile.de.dropbox;

import org.apache.commons.logging.LogFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

	public static void main(String[] args) {
		
		//added to turn off unnecessary log messages from Apache http client
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		Injector injector = Guice.createInjector(new DropBoxModule());
		
		//main service for all dropbox operations
	    DropBoxService dropBoxService = injector.getInstance(DropBoxService.class);
	    
	    dropBoxService.start(args);
	    
		
	}

}
