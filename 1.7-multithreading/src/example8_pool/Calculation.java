package example8_pool;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class Calculation implements Callable<String> {

    private static ThreadLocal<MessageDigest> localDigest = ThreadLocal.withInitial(Calculation::getMd5Digest);
    private static ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 1);

    @Override
    public String call() {
        Integer cnt = counter.get();
        counter.set(cnt + 1);
        MessageDigest md = localDigest.get();

        String s = randomString();
        byte[] bytes = s.getBytes();

        for (int i = 0; i < 40000; i++) {
            md.update(bytes);
            bytes = md.digest();
        }

        return cnt + ". " + s + ": " + bytesToHex(bytes);
    }

    public String randomString() {
        int leftLimit = 97; //a
        int rightLimit = 122; //z
        int targetStringLength = 16;
        ThreadLocalRandom random = ThreadLocalRandom.current();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static MessageDigest getMd5Digest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}
