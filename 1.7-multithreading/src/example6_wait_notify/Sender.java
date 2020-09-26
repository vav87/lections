package example6_wait_notify;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class Sender implements Runnable {
    private final MailBox mailBox;

    public Sender(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    @Override
    public void run() {
        List<String> messages = asList(
                "Hello",
                "Anybody?",
                "Hello?",
                "Please, answer me",
                "Oh, I think you're just receiver. Sorry."
        );
        Random random = new Random();

        for (String message : messages) {
            mailBox.send(message);
            System.out.println("Message sent: " + message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }

        mailBox.send("");
    }
}
