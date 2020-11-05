package ru.digitalhabbits.sbt.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping
    public String index() {
        return "Hello from " + applicationName;
    }
}
