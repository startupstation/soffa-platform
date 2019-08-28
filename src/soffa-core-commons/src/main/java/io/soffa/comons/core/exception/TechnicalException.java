package io.soffa.comons.core.exception;

public class TechnicalException extends ApplicationException {

    public TechnicalException(Throwable cause) {
        super(cause);
    }

    public TechnicalException(String code) {
        super(code);
    }

    public TechnicalException(String code, String message) {
        super(code, message);
    }

    public TechnicalException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public TechnicalException(String code,  Throwable cause) {
        super(code, cause.getMessage(), cause);
    }
}
