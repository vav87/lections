package ru.digitalhabbits.istio;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.digitalhabbits.istio.domain.Book;
import ru.digitalhabbits.istio.repository.BookRepository;

import java.util.Random;
import java.util.UUID;

import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;

@SpringBootApplication
public class BookStoreApplication
        implements ApplicationRunner {
    private static final int COUNT = 1000;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) {
        range(0, COUNT).forEach(i -> bookRepository.add(buildBook()));
    }

    private Book buildBook() {
        return new Book()
                .setUid(UUID.randomUUID())
                .setName(randomAlphabetic(10))
                .setAuthor(randomAlphabetic(7) + " " + randomAlphabetic(10))
                .setRating(nextInt(1, 5));
    }
}
