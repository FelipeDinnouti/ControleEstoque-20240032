package com.controleestoque.api_estoque.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OutOfStock extends RuntimeException {
    public OutOfStock(String message) {
        super(message);
    }
}
