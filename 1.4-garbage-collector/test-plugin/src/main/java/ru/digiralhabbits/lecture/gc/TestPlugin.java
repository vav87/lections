package ru.digiralhabbits.lecture.gc;

public class TestPlugin {
    private static String TEST_DATA = Utils.randomString(10_000);

    public String execute() {
        return TEST_DATA;
    }
}