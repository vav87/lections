package example2_thread;

public class Main {
    public static void main(String args[]) {
        DigitalHabits digitalHabits = new DigitalHabits();
        DigitalHabits2 digitalHabits2 = new DigitalHabits2();
        digitalHabits.start();
        digitalHabits2.start();
        System.out.println("I'm from the main thread.");
    }
}
