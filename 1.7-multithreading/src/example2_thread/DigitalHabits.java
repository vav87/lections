package example2_thread;

public class DigitalHabits extends Thread {

    @Override
    public void run() {
        System.out.println("I'm from a thread");
    }
}
