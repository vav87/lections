package ru.digitalhabbits.istio.repository;

import org.springframework.stereotype.Repository;
import ru.digitalhabbits.istio.domain.Book;
import ru.digitalhabbits.istio.exceptions.BookAlreadyExistsException;
import ru.digitalhabbits.istio.exceptions.BookNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Repository
public class BookRepositoryImpl
        implements BookRepository {
    private final Map<String, Book> storage = new HashMap<>();

    @Override
    public Book findBook(String name) {
        return ofNullable(storage.get(name))
                .orElseThrow(() -> new BookNotFoundException(format("Book '%s' not found", name)));
    }

    @Override
    public List<Book> findAllBooks() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Book add(Book book) {
        final String name = book.getName();
        if (storage.containsKey(name)) {
            throw new BookAlreadyExistsException(format("Book '%s' already exists", name));
        }
        storage.put(name, book);
        return book;
    }

    @Override
    public Book delete(String name) {
        final Book removedBook = storage.remove(name);
        if (removedBook == null) {
            throw new BookNotFoundException(format("Book '%s' not found", name));
        }
        return removedBook;
    }
}
