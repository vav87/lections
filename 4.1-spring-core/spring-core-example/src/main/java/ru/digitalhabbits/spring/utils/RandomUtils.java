package ru.digitalhabbits.spring.utils;

import java.security.SecureRandom;

public class RandomUtils {

    private static final String TOKENS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(TOKENS.charAt(rnd.nextInt(TOKENS.length())));
        }

        return sb.toString();
    }
}
