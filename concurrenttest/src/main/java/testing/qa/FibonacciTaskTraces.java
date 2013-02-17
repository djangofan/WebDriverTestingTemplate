package testing.qa;

import java.util.concurrent.RecursiveTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FibonacciTaskTraces extends RecursiveTask<Long> {
	
	// http://www.sw-engineering-candies.com/blog-1/howtoimplementforkandjoininjava7-jsr166concurrencyutilities

	private static final long serialVersionUID = -6523988045676139867L;

	public static Logger logger = LoggerFactory.getLogger( "JUnit" );

    public static final String OUTPUT_PREFIX = " | ";

    private final String prefix;

    private final long inputValue;

    public FibonacciTaskTraces(long inputValue, final String prefix) {
        this.inputValue = inputValue;
        this.prefix = prefix;
    }

    @Override
    public Long compute() {

        if (inputValue == 0L) {
            slowTask();
            return 0L;
        } else if (inputValue <= 2L) {
            slowTask();
            return 1L;
        } else {
            final long firstValue = inputValue - 1L;
            logger.info(prefix + " - Fibonacci(" + firstValue + ") <- " 
                    + Thread.currentThread().getName()
                    + " (fork) ");
            final FibonacciTaskTraces firstWorker = 
                    new FibonacciTaskTraces(firstValue, prefix + OUTPUT_PREFIX);
            firstWorker.fork();

            final long secondValue = inputValue - 2L;
            logger.info(prefix + " - Fibonacci(" + secondValue + ") <- " 
                    + Thread.currentThread().getName());
            final FibonacciTaskTraces secondWorker = 
                    new FibonacciTaskTraces(secondValue, prefix + OUTPUT_PREFIX);
        
            long result = secondWorker.compute() + firstWorker.join();
            logger.info(prefix + " - Fibonacci(" + inputValue + ") = " 
                    + result + " <- "
                    + Thread.currentThread().getName() + " (join)");
            slowTask();

            return result;
        }
    }

    /** just simulate a longer running task (with out disturbing the other threads) */
    private void slowTask() {
        for (int k = 0, i = 0; i < 1000 * 1000 * 100; i++) {
            i = i + k;
        }
    }
}
