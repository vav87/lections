package ru.digitalhabbits.sb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

public class TestClass {
    private static final Logger logger = LoggerFactory.getLogger(TestClass.class);

    @PostConstruct
    public void init() {
        logger.info("TEST");
    }
}
