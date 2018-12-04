package com.bttc.HappyGraduation.utils.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 4242111829918405178L;

    private String code;
    private String message;
    private ErrorCode errorCode;

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(ErrorCode errorCode, String... message) {
        errorCode.getMessageAndCompletion(message);
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();

    }

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static void throwBusinessException(ErrorCode errorCode, String... message) {
        throw new BusinessException(errorCode, message);
    }

    public static void throwBusinessException(ErrorCode errorCode) {
        throw new BusinessException(errorCode);
    }

    public static void throwBusinessException(String message, Throwable cause) {
        throw new BusinessException(message, cause);
    }

    public static void throwBusinessException(Throwable cause) {
        throw new BusinessException(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
