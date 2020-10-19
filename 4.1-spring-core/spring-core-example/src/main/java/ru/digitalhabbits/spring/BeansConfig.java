package ru.digitalhabbits.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import static ru.digitalhabbits.spring.utils.RandomUtils.generateRandomString;

@Configuration
public class BeansConfig {

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public StringWrapper message() {
        return new StringWrapper(generateRandomString(3));
    }
}
