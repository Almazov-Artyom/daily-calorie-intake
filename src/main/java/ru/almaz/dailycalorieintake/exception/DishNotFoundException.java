package ru.almaz.dailycalorieintake.exception;

public class DishNotFoundException extends RuntimeException {
  public DishNotFoundException(String message) {
    super(message);
  }
}
