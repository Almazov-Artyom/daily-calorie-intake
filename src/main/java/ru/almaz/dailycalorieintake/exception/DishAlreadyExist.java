package ru.almaz.dailycalorieintake.exception;

public class DishAlreadyExist extends RuntimeException {
    public DishAlreadyExist(String message) {
        super(message);
    }
}
