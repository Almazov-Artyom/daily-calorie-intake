package ru.almaz.dailycalorieintake.exception;

public class UserUnauthenticatedException extends RuntimeException {
    public UserUnauthenticatedException(String message) {
        super(message);
    }
}
