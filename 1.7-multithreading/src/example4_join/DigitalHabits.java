package example4_join;

public class DigitalHabits implements Runnable {

    public void run() {
        System.out.println("Thread: start job for 3 sec");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread: job completed");
    }
}
