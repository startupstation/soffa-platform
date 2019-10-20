package io.soffa.core.security;

public class InvalidAuthException extends Exception {

    public InvalidAuthException() {
        super();
    }

    public InvalidAuthException(String message) {
        super(message);
    }
}
