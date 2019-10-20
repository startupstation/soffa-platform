package io.soffa.core.exception;

public class FunctionalException extends ApplicationException {

    public FunctionalException(String code) {
        super(code);
    }

    public FunctionalException(String code, String message) {
        super(code, message);
    }

    public FunctionalException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

