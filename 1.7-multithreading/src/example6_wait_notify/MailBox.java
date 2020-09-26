package example6_wait_notify;

public class MailBox {
    private String message;

    private boolean empty = true;

    public synchronized String get() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

        empty = true;
        notifyAll();
        return message;
    }

    public synchronized void send(String message) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

        empty = false;
        this.message = message;
        notifyAll();
    }
}
