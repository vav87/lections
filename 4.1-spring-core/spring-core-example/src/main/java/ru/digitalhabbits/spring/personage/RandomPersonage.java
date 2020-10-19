package ru.digitalhabbits.spring.personage;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.digitalhabbits.spring.annotations.InjectRandomMessage;
import ru.digitalhabbits.spring.annotations.Timed;

@Timed
@Component
public class RandomPersonage
        implements Personage {

    @InjectRandomMessage(length = 3)
    private String message;

    @Override
    public void say() {
        System.out.println(message);
    }
}
