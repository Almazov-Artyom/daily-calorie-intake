package ru.almaz.dailycalorieintake.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.almaz.dailycalorieintake.exception.*;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            UserAlreadyExistException.class,
            UsernameNotFoundException.class,
            InvalidPurposeException.class,
            InvalidGenderException.class,
            DishAlreadyExist.class,
            DishNotFoundException.class
    })
    public ProblemDetail handleUserAlreadyExistException(RuntimeException e) {
        ProblemDetail response = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,e.getMessage());
        return response;
    }

    @ExceptionHandler({
            UserUnauthenticatedException.class,
            InvalidTokenException.class,
            InvalidRefreshTokenException.class
    })
    public ProblemDetail handleUserUnauthenticatedException(RuntimeException e) {
        ProblemDetail response = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,e.getMessage());
        return response;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException e) {
        ProblemDetail response = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,e.getMessage());
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail response = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Error");
        response.setProperty(
                "errors",
                ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList()
        );
        return response;
    }
}
