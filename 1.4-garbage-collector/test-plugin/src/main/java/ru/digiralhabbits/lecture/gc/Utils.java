package ru.digiralhabbits.lecture.gc;

import java.util.Random;

public final class Utils {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int SIZE = 26;
    private static final Random random = new Random(System.currentTimeMillis());

    public static String randomString(int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int pos = random.nextInt(SIZE);
            result.append(ALPHABET[pos]);
        }
        return result.toString();
    }
}
