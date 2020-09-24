package example4_join;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        Thread thread = new Thread(new DigitalHabits());
        thread.start();

        System.out.println("Main  : Waiting for thread for 2 sec");
        thread.join(2000);
        if (thread.isAlive()) {
            System.out.println("Main  : thread is alive. waiting for completion");
            thread.join();
            if (thread.isAlive()) {
                throw new RuntimeException("It is impossible!!!");
            }
        }
        System.out.println("Main  : thread completed");
    }
}
