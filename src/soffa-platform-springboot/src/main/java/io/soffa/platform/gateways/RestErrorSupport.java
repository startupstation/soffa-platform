package io.soffa.platform.gateways;

import io.soffa.platform.core.security.InvalidAuthException;
import org.apache.http.HttpStatus;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

public class RestErrorSupport {

    public static Map<String, Object> getErrorAttributes(Map<String, Object> attributes, Throwable ex) {
        String message = ex.getMessage();
        if (ex instanceof UndeclaredThrowableException) {
            message = ex.getCause().getMessage();
            if (ex.getCause() instanceof InvalidAuthException) {
                attributes.put("status", HttpStatus.SC_FORBIDDEN);
            }
        }
        attributes.put("message", message);
        return attributes;
    }

}
