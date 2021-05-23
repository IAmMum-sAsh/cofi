package ru.mirea.cofi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type My not found exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyNotFoundException extends RuntimeException{
    /**
     * Instantiates a new My not found exception.
     *
     * @param message the message
     */
    public MyNotFoundException(String message) {
        super(message);
    }
}
