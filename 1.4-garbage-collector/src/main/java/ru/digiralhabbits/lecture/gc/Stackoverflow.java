package ru.digiralhabbits.lecture.gc;

public class Stackoverflow {
    private static int counter = 0;

    public static void main(String[] args) {
        // Вызываем рекурсивно метод без локальных аргументов
        // f0(); // <-- 9270
        // Вызываем рекурсивно метод с 8 локальными аргументами
        // f8(); // <-- 5935
        // Вызываем рекурсивно метод с параметрами и 8 локальными аргументами
        // f8(0, 0, 0, 0, 0, 0, 0, 0); // <-- 3860
    }

    private static void f0() {
        System.out.println(counter++);
        f0();
    }

    private static void f8() {
        int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, k = 0;
        System.out.println(counter++);
        f8();
    }

    private static void f8(int a, int b, int c, int d, int e, int f, int g, int k) {
        int a0 = a++, b0 = b++, c0 = c++, d0 = d++, e0 = e++, f0 = f++, g0 = g++, k0 = k++;
        System.out.println(counter++);
        f8(a0, b0, c0, d0, e0, f0, g0, k0);
    }
}