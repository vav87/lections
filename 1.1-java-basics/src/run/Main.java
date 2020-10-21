package run;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello");

        LinkedList<String> words = new LinkedList<>();
        words.add("Java");
        words.add("Spring");

        for (String word : words) {
            System.out.println(word);
        }

        words.addLast("");
    }
}