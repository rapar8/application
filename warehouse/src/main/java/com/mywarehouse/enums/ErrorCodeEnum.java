package com.mywarehouse.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCodeEnum {

    USER_NOT_SIGNED_IN(HttpStatus.BAD_REQUEST, "UserNotSignedIn"),
    STOCK_NOT_AVAILABLE(HttpStatus.NOT_FOUND, "StockNotAvailable"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NotFound"),
    FILE_SIZE_EXCEEDED(HttpStatus.PAYLOAD_TOO_LARGE, "FileSizeExceeded"),
    FILE_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "FileType"),
    FILE_MISSING(HttpStatus.BAD_REQUEST, "FileMissing"),
    FILE_CONTENT(HttpStatus.BAD_REQUEST, "FileContent"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "ServiceUnavailable"),
    SERVICE_TEMPORARY_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "ServiceTemporaryUnavailable"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "OrderNotFound"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UnexpectedError"),
    HEADER_MISSING(HttpStatus.BAD_REQUEST, "HeaderMissing"),
    PARAMETER_MISSING(HttpStatus.BAD_REQUEST, "ParameterMissing");

    final private HttpStatus httpStatus;
    final private String code;


    ErrorCodeEnum(HttpStatus httpStatus, String code) {
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return code;
    }

}
