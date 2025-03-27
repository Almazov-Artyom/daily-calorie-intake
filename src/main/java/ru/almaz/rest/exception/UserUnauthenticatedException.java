package ru.almaz.rest.exception;

public class UserUnauthenticatedException extends RuntimeException {
    public UserUnauthenticatedException(String message) {
        super(message);
    }
}
