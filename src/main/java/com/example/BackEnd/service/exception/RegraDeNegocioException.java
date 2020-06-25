package com.example.BackEnd.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Erro de negócio.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(final String message) {
        this(message, null);
    }

    public RegraDeNegocioException(final String message, final Throwable cause) {
        super(message, cause);
    }


}
