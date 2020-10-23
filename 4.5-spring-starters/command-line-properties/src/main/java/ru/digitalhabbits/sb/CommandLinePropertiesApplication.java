package ru.digitalhabbits.sb;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import static org.slf4j.LoggerFactory.getLogger;

/*
 * в program arguments прописать --disable.web.application
 */
@SpringBootApplication
public class CommandLinePropertiesApplication
        implements CommandLineRunner {
    private static final Logger logger = getLogger(CommandLinePropertiesApplication.class);
    private static final String DISABLE_WEB_APPLICATION = "disable.web.application";

    public static void main(String[] args) {
        final SimpleCommandLinePropertySource simpleCommandLinePropertySource =
                new SimpleCommandLinePropertySource(args);
        final String property = simpleCommandLinePropertySource.getProperty(DISABLE_WEB_APPLICATION);
        final WebApplicationType type = property != null ? WebApplicationType.NONE : WebApplicationType.SERVLET;

        new SpringApplicationBuilder()
                .sources(CommandLinePropertiesApplication.class)
                .web(type)
                .run(args);
    }

    @Override
    public void run(String... args) {
        logger.info("Running...");
    }
}
