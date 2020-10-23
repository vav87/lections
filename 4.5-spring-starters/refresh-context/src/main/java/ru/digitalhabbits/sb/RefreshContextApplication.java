package ru.digitalhabbits.sb;

import org.slf4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.slf4j.LoggerFactory.getLogger;

public class RefreshContextApplication {
    private static final Logger logger = getLogger(RefreshContextApplication.class);

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        logger.info("Register TestClass");
        context.register(TestClass.class);
        logger.info("Refresh context");
        context.refresh();
        final TestClass bean = context.getBean(TestClass.class);
        logger.info("Get bean from context '{}'", bean);
        context.refresh();
    }
}
