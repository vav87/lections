package ru.digitalhabbits.sb.config.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.digitalhabbits.sb.config.service.ApplicationInfoPrinter;
import ru.digitalhabbits.sb.config.service.ApplicationNamePrinter;

@Configuration
@ConditionalOnMissingBean(value = ApplicationInfoPrinter.class)
@ConditionalOnProperty(name = "application.info.printer.enabled", matchIfMissing = true)
public class ApplicationPrinterAutoconfiguration {

    @Bean
    public ApplicationInfoPrinter printer() {
        return new ApplicationNamePrinter();
    }
}
