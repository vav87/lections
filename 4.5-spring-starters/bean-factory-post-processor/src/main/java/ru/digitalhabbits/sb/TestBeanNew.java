package ru.digitalhabbits.sb;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;

import static org.slf4j.LoggerFactory.getLogger;

public class TestBeanNew
        implements TestBean {
    private static final Logger logger = getLogger(TestBeanNew.class);

    @PostConstruct
    public void init() {
        logger.info("Init TestBeanNew");
    }
}
