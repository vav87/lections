public class Main {

    public static void main(String[] args) throws InterruptedException {
        Sample sample = new Sample();

        Thread thread1 = new Thread(sample);
        thread1.start();

        Thread thread2 = new Thread(sample);
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(sample.value);
    }
}

class Sample implements Runnable {

    public int value = 0;
    private final Object monitor = new Object();

    @Override
    public void run() {
        for (int i = 0; i < 1000000000; i++) {
            synchronized (monitor) {
                value++;
            }
        }
    }
}

