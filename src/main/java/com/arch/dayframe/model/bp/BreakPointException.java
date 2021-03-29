package com.arch.dayframe.model.bp;

import java.security.InvalidParameterException;
import java.util.Optional;

public class BreakPointException extends RuntimeException {

    private ErrorCode errorCode;
    private String errorSource;

    public enum ErrorCode {
        DESCRIPTION_FORMAT_ERR, TIME_FORMAT_ERR, TIME_DUPLICATE_ERR,
        ALREADY_POSTPONED_ERR, ZERO_POSTPONE_ERR, NEGATIVE_POSTPONE_ERR
    }

    public BreakPointException(ErrorCode errorCode) {
        this(errorCode, "[NO-SOURCE]");
    }

    public BreakPointException(ErrorCode errorCode, String errorSource) {
        this.errorCode = Optional.ofNullable(errorCode).orElseThrow(InvalidParameterException::new);
        this.errorSource = Optional.ofNullable(errorSource).orElseThrow(InvalidParameterException::new);
    }

    @Override
    public String getMessage() {
        switch (errorCode) {
            case DESCRIPTION_FORMAT_ERR:
                return String.format("Wrong break point description: %s", errorSource);
            case TIME_FORMAT_ERR:
                return String.format("Wrong break point time: %s", errorSource);
            case TIME_DUPLICATE_ERR:
                return String.format("Duplicated break point time: %s", errorSource);
            case ALREADY_POSTPONED_ERR:
                return "Break point has been already postponed.";
            case ZERO_POSTPONE_ERR:
                return "Wrong postponement value: 0";
            case NEGATIVE_POSTPONE_ERR:
                return String.format("Wrong postponement value: %s", errorSource);
        }
        return "";
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
