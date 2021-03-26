package com.arch.dayframe.model.bp;

public class BreakPointException extends Exception {

    private ErrorCode errorCode;
    private String errorSource;

    public enum ErrorCode {
        DESCRIPTION_FORMAT_ERR, TIME_FORMAT_ERR,
        ALREADY_POSTPONED_ERR, ZERO_POSTPONE_ERR, NEGATIVE_POSTPONE_ERR
    }

    public BreakPointException(String message) {
        super(message);
    }

    public BreakPointException(ErrorCode errorCode) {
        this(errorCode, "");
    }

    public BreakPointException(ErrorCode errorCode, String errorSource) {
        this.errorCode = errorCode;
        this.errorSource = errorSource;
    }

    @Override
    public String getMessage() {
        switch (errorCode) {
            case DESCRIPTION_FORMAT_ERR:
                return String.format("Wrong break point description: %s", errorSource);
            case TIME_FORMAT_ERR:
                return String.format("Wrong break point time: %s", errorSource);
            case ALREADY_POSTPONED_ERR:
                return "Break point has been already postponed.";
            case ZERO_POSTPONE_ERR:
                return "Wrong postponement value: 0";
            case NEGATIVE_POSTPONE_ERR:
                return String.format("Wrong postponement value: %s", errorSource);
            default:
                return super.getMessage();
        }
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
