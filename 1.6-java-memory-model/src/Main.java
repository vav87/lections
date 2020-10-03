import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static void main(String[] args) {
        ReorderingSample sample = new ReorderingSample();

        Thread thread1 = new Thread(sample::setValues);
        thread1.start();

        Thread thread2 = new Thread(sample::checkValues);
        thread2.start();
    }
}

class ReorderingSample {
    private volatile boolean result = false;
    private volatile boolean resultReady = false;
    private final Object lock = new Object();

    void setValues() {
        synchronized (lock) {
            result = calculateResult();
            resultReady = true;
        }
    }

    void checkValues() {
        synchronized (lock) {
            while (!resultReady) Thread.onSpinWait();
            System.out.println(result);
        }
    }

    boolean calculateResult() {
        return true;
    }
}
