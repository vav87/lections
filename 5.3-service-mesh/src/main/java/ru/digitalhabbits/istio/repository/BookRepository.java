package ru.digitalhabbits.istio.repository;

import ru.digitalhabbits.istio.domain.Book;

import java.util.List;

public interface BookRepository {
    Book findBook(String name);
    List<Book> findAllBooks();
    Book add(Book book);
    Book delete(String name);
}
