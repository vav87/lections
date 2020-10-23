package ru.digitalhabbits.sb;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication(exclude = ActiveMQAutoConfiguration.class)
//@EnableScheduling
public class AutoconfigurationApplication
        implements ApplicationRunner,
                   ApplicationContextAware {
    private static final Logger logger = getLogger(AutoconfigurationApplication.class);

    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(AutoconfigurationApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Bean count {}", context.getBeanDefinitionCount());
        logger.info("Beans:\n{}", String.join("\n", List.of(context.getBeanDefinitionNames())));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }
}
