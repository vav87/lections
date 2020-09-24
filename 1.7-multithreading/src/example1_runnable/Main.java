package example1_runnable;

public class Main {
    public static void main(String args[]) {
        Thread thread = new Thread(new DigitalHabits());
        thread.start();
        System.out.println("I'm from the main thread.");
    }
}
