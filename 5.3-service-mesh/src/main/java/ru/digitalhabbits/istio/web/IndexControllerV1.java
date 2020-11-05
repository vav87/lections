package ru.digitalhabbits.istio.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class IndexControllerV1 {

    @GetMapping
    public String index() {
        throw new RuntimeException("Wrong method");
    }

    @GetMapping("/ping")
    public String ping() {
        return "OK";
    }
}
