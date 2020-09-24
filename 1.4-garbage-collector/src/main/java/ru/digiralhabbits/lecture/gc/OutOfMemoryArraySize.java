package ru.digiralhabbits.lecture.gc;

// -Xmx64M
public class OutOfMemoryArraySize {
    public static void main(String[] args) {
        int[] arr = new int[dynamic()];
        System.out.println(arr.length);
    }

    private static int dynamic() {
        return Integer.MAX_VALUE;
    }
}