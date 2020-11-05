package ru.digitalhabbits.istio.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class IndexControllerV2 {

    @Value("${spring.application.name}")
    public String applicationName;

    @GetMapping
    public String index() {
        return "Hello from " + applicationName;
    }
}
