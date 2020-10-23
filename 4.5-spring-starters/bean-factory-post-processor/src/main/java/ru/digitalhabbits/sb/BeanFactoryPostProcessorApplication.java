package ru.digitalhabbits.sb;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BeanFactoryPostProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeanFactoryPostProcessorApplication.class, args);
    }

    @Bean
    public static BeanFactoryPostProcessor deprecatedBeanFactoryPostProcessor() {
        return new DeprecatedBeanBeanFactoryPostProcessor();
    }
}