package example6_wait_notify;

import java.util.Random;

public class Receiver implements Runnable {
    private final MailBox mailBox;

    public Receiver(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (String message = mailBox.get(); !message.isEmpty(); message = mailBox.get()) {
            System.out.println("Message received: " + message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ignored) {}
        }
    }
}
