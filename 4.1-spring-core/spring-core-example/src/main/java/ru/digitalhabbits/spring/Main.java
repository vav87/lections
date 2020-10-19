package ru.digitalhabbits.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.digitalhabbits.spring.personage.Joker;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        Joker joker = context.getBean(Joker.class);
        joker.say();

        Joker joker1 = context.getBean(Joker.class);
        joker1.say();

        context.registerShutdownHook();
    }
}
