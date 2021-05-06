package ru.mirea.cofi.security.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Jwt invalid access token exception.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtInvalidAccessTokenException extends BadRequestException {
    private static final long serialVersionUID = 1L;
}
