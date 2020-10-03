package example8_pool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Calendar start = Calendar.getInstance();

        int threadsCount = 400;
        int tasksCount = 1000;
        List<Future<String>> futures = new ArrayList<>(tasksCount);
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < tasksCount; i++) {
            futures.add(service.submit(new Calculation()));
        }

        service.shutdown();

        for (Future<String> f : futures) {
            System.out.println(f.get());
        }
        Calendar stop = Calendar.getInstance();


        System.out.println("Total time: " + (stop.getTimeInMillis() - start.getTimeInMillis()) + " ms");

    }
}
