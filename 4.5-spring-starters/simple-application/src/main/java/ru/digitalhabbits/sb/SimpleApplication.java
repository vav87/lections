package ru.digitalhabbits.sb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.sb.config.ApplicationPrinterAutoconfiguration;
import ru.sb.config.service.ApplicationInfoPrinter;

@SpringBootApplication
@Import(ApplicationPrinterAutoconfiguration.class)
public class SimpleApplication
        implements CommandLineRunner {

    @Autowired
    private ApplicationInfoPrinter printer;

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        printer.print();
    }
}
