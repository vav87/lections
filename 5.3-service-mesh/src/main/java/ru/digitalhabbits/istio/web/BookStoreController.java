package ru.digitalhabbits.istio.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.digitalhabbits.istio.model.BookInfoRequest;
import ru.digitalhabbits.istio.model.BookInfoResponse;
import ru.digitalhabbits.istio.service.BookStoreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookStoreController {
    private final BookStoreService bookStoreService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookInfoResponse> bookInfo(@RequestParam(required = false) String name) {
        return bookStoreService.findBooks(name);
    }

    @PostMapping(value = "/sell",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BookInfoResponse sell(@RequestBody BookInfoRequest request) {
        return bookStoreService.sell(request);
    }

    @PostMapping(value = "/purchase", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookInfoResponse purchase(@RequestParam String name) {
        return bookStoreService.purchase(name);
    }
}
