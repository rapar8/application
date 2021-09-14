package com.mywarehouse.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mywarehouse.enums.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @JsonIgnore
    private String timestamp;
    private String error;
    private String message;
    private String code;
    @JsonIgnore
    private Integer status;
    @JsonIgnore
    private String path;
    @JsonAlias("error_description")
    private String errorDescription;

    public ErrorResponse(String message, ErrorCodeEnum errorCodeEnum) {

        this.code = errorCodeEnum.getErrorCode();
        this.message = message;
        this.error = null;
    }

    public ErrorResponse(String message, Exception ex) {

        this.code = ErrorCodeEnum.UNEXPECTED_ERROR.getErrorCode();
        this.message = message;
        this.error = ex.getMessage();
    }
}
