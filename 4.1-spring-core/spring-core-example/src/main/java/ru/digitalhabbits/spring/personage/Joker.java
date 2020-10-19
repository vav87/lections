package ru.digitalhabbits.spring.personage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.digitalhabbits.spring.StringWrapper;

@Component
public class Joker implements Personage {

    private StringWrapper message;

    @Autowired
    public void setMessage(StringWrapper message) {
        this.message = message;
    }

    @Override
    public void say() {
        String str = new String();
        System.out.println(message.getStr());
    }
}
