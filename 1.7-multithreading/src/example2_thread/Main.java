package example2_thread;

public class Main {
    public static void main(String args[]) {
        DigitalHabits digitalHabits = new DigitalHabits();
        digitalHabits.start();
        System.out.println("I'm from the main thread.");
    }
}
