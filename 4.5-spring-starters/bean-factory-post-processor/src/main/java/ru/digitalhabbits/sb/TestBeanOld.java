package ru.digitalhabbits.sb;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@Deprecated
//@DeprecatedClass(newImpl = TestBeanNew.class)
public class TestBeanOld
        implements TestBean {
    private static final Logger logger = getLogger(TestBeanOld.class);

    @PostConstruct
    public void init() {
        logger.info("Init TestBeanOld");
    }
}
