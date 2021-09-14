package com.mywarehouse.component;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.mywarehouse.enums.ErrorCodeEnum;
import com.mywarehouse.exceptoin.StockNotAvailableException;
import com.mywarehouse.model.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingHeader(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage(), ex));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameterException(Exception ex) {
        log.error("Exception: Class {}|{}", ex.getClass().getCanonicalName(), ex.getMessage(), ex);
        String errorMessage = getTranslatedMessage(ErrorCodeEnum.PARAMETER_MISSING, ex.getMessage());

        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage, ErrorCodeEnum.PARAMETER_MISSING));
    }

    @ExceptionHandler(StockNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        log.error("Exception: Class {}|{}", ex.getClass().getCanonicalName(), ex.getMessage(), ex);
        String errorMessage = getTranslatedMessage(ErrorCodeEnum.STOCK_NOT_AVAILABLE, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errorMessage, ErrorCodeEnum.STOCK_NOT_AVAILABLE));
    }

    @ExceptionHandler(org.springframework.web.multipart.MultipartException.class)
    public ResponseEntity<ErrorResponse> handleMissingFileParameterException(Exception ex) {
        log.error("Exception: Class {}|{}", ex.getClass().getCanonicalName(), ex.getMessage(), ex);
        String errorMessage = getTranslatedMessage(ErrorCodeEnum.PARAMETER_MISSING, ex.getMessage());

        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage, ErrorCodeEnum.PARAMETER_MISSING));
    }

    @ExceptionHandler(com.fasterxml.jackson.databind.exc.MismatchedInputException.class)
    public ResponseEntity<ErrorResponse> handleMissingFileException(Exception ex) {
        log.error("Exception: Class {}|{}", ex.getClass().getCanonicalName(), ex.getMessage(), ex);

        String errorMessage = getTranslatedMessage(ErrorCodeEnum.FILE_MISSING, ex.getMessage());

        return ResponseEntity.badRequest().body(new ErrorResponse("Check File content", ErrorCodeEnum.FILE_MISSING));
    }

    @ExceptionHandler({com.fasterxml.jackson.databind.JsonMappingException.class, UnrecognizedPropertyException.class})
    public ResponseEntity<ErrorResponse> handleJsonFormatException(Exception ex) {
        log.error("Exception: Class {}|{}", ex.getClass().getCanonicalName(), ex.getMessage(), ex);

        String errorMessage = getTranslatedMessage(ErrorCodeEnum.FILE_CONTENT, ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage, ErrorCodeEnum.FILE_CONTENT));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    private String getTranslatedMessage(ErrorCodeEnum errorCodeEnum, String defaultMessage) {
        return this.messageSource.getMessage(errorCodeEnum.getErrorCode(), null, defaultMessage, LocaleContextHolder.getLocale());
    }

    private String getTranslatedMessage(String defaultMessage) {
        return this.messageSource.getMessage(defaultMessage, null, defaultMessage, LocaleContextHolder.getLocale());
    }
}
