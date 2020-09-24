package example3_sleep_and_interrupts;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        Thread thread = new Thread(new DigitalHabits());
        thread.start();

        Thread.sleep(1000);
        thread.interrupt();
    }
}
