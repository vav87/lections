package example3_sleep_and_interrupts;

import static java.lang.Thread.currentThread;

public class DigitalHabits implements Runnable {

    public void run() {
        while (true) {
            for(int i = 0; i < 500000; i++) {
                if (currentThread().isInterrupted()) {
                    System.out.println("Interrupted in active work");
                    break;
                }
            }
            if (currentThread().isInterrupted()) {
                break;
            }
            System.out.print('.');

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Interrupted in waiting mode");
                break;
            }
        }
    }
}
