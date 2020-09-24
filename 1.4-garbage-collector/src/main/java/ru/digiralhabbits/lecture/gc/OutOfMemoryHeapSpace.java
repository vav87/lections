package main.java.ru.digiralhabbits.lecture.gc;

public class OutOfMemoryHeapSpace {
    public static void main(String[] args) {
        int size = 1_000_000;
        int[][] arr = new int[size][];
        for (int i = 0; i < size; i++) {
            arr[i] = new int[size];
        }
        System.out.println(arr.length);
    }
}
