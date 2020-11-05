package ru.digitalhabbits.istio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.digitalhabbits.istio.domain.Book;
import ru.digitalhabbits.istio.model.BookInfoRequest;
import ru.digitalhabbits.istio.model.BookInfoResponse;
import ru.digitalhabbits.istio.repository.BookRepository;

import java.util.List;
import java.util.UUID;

import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class BookStoreService {
    private final BookRepository bookRepository;

    public List<BookInfoResponse> findBooks(String name) {
        if (!isEmpty(name)) {
            return of(buildBookInfoResponse(bookRepository.findBook(name)));
        }
        return bookRepository
                .findAllBooks()
                .stream()
                .map(this::buildBookInfoResponse)
                .collect(toList());
    }

    public BookInfoResponse sell(BookInfoRequest request) {
        final Book book = new Book()
                .setUid(UUID.randomUUID())
                .setName(request.getName())
                .setAuthor(request.getAuthor())
                .setRating(request.getRating());
        bookRepository.add(book);
        return buildBookInfoResponse(book);
    }

    public BookInfoResponse purchase(String name) {
        return buildBookInfoResponse(bookRepository.delete(name));
    }

    private BookInfoResponse buildBookInfoResponse(Book book) {
        return new BookInfoResponse()
                .setUid(book.getUid())
                .setName(book.getName())
                .setAuthor(book.getAuthor())
                .setRating(book.getRating());
    }
}
