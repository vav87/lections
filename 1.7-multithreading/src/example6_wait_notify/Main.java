package example6_wait_notify;

public class Main {
    public static void main(String[] args) {
        MailBox mailBox = new MailBox();
        (new Thread(new Receiver(mailBox))).start();
        (new Thread(new Sender(mailBox))).start();
    }
}
