package testing.qa;

import java.util.concurrent.RecursiveTask;
import static testing.qa.AllTests.logger;

public class HtmlUnitTask extends RecursiveTask<String> {
	
	// http://www.sw-engineering-candies.com/blog-1/howtoimplementforkandjoininjava7-jsr166concurrencyutilities

	private static final long serialVersionUID = -6523988045676139867L;

    private final String inputValue;

    public HtmlUnitTask(String inputValue) {
        this.inputValue = inputValue;
    }

    /** just simulate a longer running task (with out disturbing the other threads) */
    private void slowTask() {
        for (int k = 0, i = 0; i < 1000 * 1000 * 100; i++) {
            i = i + k;
        }
    }

    @Override
    protected String compute() {
    	
    	// code a file visitor that searches for .xml files
	// http://www.ibm.com/developerworks/java/library/j-nio2-2/

        logger.info( "compute() has: " + inputValue );
        slowTask();
	return inputValue;
    }
		
}
