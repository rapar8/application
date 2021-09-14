package com.mywarehouse.exceptoin;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StockNotAvailableException extends RuntimeException {

    public StockNotAvailableException(String message) {
        super(message);
    }

    public StockNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
