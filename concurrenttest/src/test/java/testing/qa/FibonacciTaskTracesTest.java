package testing.qa;

import java.util.concurrent.ForkJoinPool;
import static testing.qa.FibonacciTaskTraces.logger;
import org.junit.Assert;
import org.junit.Test;


public class  FibonacciTaskTracesTest {

    // it makes no sense to create more threads than available cores (no speed improvement here)
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    // create thread pool
    private final ForkJoinPool pool = new ForkJoinPool(AVAILABLE_PROCESSORS);

    @Test
    public void testFibonacciArrayTraces() {

        // more test data: 
        // http://www.maths.surrey.ac.uk/hosted-sites/R.Knott/Fibonacci/fibtable.html
        long results[] = { 0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L };
        for (int inputValue = 0; inputValue < results.length; inputValue++) {

            final FibonacciTaskTraces task = new FibonacciTaskTraces(inputValue, " | ");
            logger.info("invoke Fibonacci(" + inputValue + ")  <- " + Thread.currentThread().getName());

            final long result = pool.invoke(task);
            logger.info("result = " + result + "\n");

            Assert.assertEquals(results[inputValue], result);
        }
    }
	
}
