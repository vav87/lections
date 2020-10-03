package example2_thread;

public class DigitalHabits extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.print("+");
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
