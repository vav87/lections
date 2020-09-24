package main.java.ru.digiralhabbits.lecture.gc;

import java.util.Random;

public class MemoryAnalyze {
    public static void main(String[] args) {
        int size = 100_000;
        final Random random = new Random(System.currentTimeMillis());
        while (true) {
            int data = random.nextInt(size);
            if (data % 100 == 0) {
                System.out.println(data);
            }
        }
    }
}
