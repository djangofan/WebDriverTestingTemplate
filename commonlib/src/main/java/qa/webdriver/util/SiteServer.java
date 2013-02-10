package qa.webdriver.util;

import java.io.*;

/**
 * Implementation of a simple web server to locally serve project files
 * for Selenium unit test projects.
 */
public class SiteServer extends NanoHTTPD {
	
	public SiteServer( int intPort, File rootDir ) throws IOException {
		super( intPort, rootDir );		
	}

	public static void main( String[] args )
	{
		try {
			new SiteServer( Integer.parseInt(args[0]), new File( args[1]) );
		} catch( IOException ioe ) {
			System.err.println( "Couldn't start server:\n" + ioe );
			System.exit( -1 );
		}
		System.out.println( "Listening on port 8080. Hit Enter to stop.\n" );
		try { System.in.read(); } catch( Throwable t ) {};
	}
	
}
