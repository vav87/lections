package ru.digitalhabbits.istio.exceptions;

public class BookAlreadyExistsException
        extends RuntimeException {
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
