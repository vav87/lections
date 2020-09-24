package ru.digiralhabbits.lecture.gc;

import static java.util.stream.IntStream.range;

public class OutOfMemoryNativeThread {
    public static void main(String[] args) {
        int size = 1_000_000;
        range(1, size).forEach(OutOfMemoryNativeThread::createThread);
    }

    private static void createThread(int i) {
        new Thread(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException ignored) {}
        }).start();
        System.out.printf("Created %d threads\n", i);
    }
}
