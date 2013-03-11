package qa.webdriver.util;

import java.io.File;
import java.text.DecimalFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CoreUtils {

	public static Logger staticlogger = LoggerFactory.getLogger( "StaticLogger" );
	protected Logger classlogger = LoggerFactory.getLogger( getClass() );

	/**
	 * This can load a gradle resource, such as a .properties file.
	 * 
	 * @param fileName
	 * @return
	 */
	public static File loadGradleResource( String fileName ) {
		File junitFile = new File("build/resources/test/" + fileName );
		if ( junitFile.exists() ) {
			staticlogger.info( "The file '" + junitFile.getAbsolutePath() + "' exists." );
		} else {
			staticlogger.info( "Problem loading Gradle resource: " + junitFile.getAbsolutePath() );
		}	
		return junitFile;
	}
	
	public static void waitTimer( int units, int mills ) {
    	DecimalFormat df = new DecimalFormat("###.##");
		double totalSeconds = ((double)units*mills)/1000;
		staticlogger.info("Explicit pause for " + df.format(totalSeconds) + " seconds divided by " + units + " units of time: ");
		try {
			Thread.currentThread();		
			int x = 0;
			while( x < units ) {
				Thread.sleep( mills );
				staticlogger.info(".");
				x = x + 1;
			}
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}	
	}

	protected CoreUtils() {
        // do nothing
	}

}
