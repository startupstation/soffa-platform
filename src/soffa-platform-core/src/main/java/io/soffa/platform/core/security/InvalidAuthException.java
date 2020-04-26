package io.soffa.platform.core.security;

public class InvalidAuthException extends Exception {

    public InvalidAuthException() {
        super();
    }

    public InvalidAuthException(String message) {
        super(message);
    }
}
