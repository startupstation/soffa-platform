package io.soffa.platform.core.exception;

public abstract class ApplicationException extends RuntimeException {

    private String code;

    private ApplicationException() {
    }

    public ApplicationException(String code) {
        super(code);
        this.code = code;
    }

    public ApplicationException(String code, String message) {
        super(code + "-" + message);
    }

    public ApplicationException(String code, String message, Throwable cause) {
        super(code + "-" + message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
