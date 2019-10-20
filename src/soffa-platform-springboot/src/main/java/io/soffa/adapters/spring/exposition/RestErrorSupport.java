package io.soffa.adapters.spring.exposition;

import io.soffa.core.security.InvalidAuthException;
import org.apache.http.HttpStatus;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

public class RestErrorSupport {

    public static Map<String, Object> getErrorAttributes(Throwable ex) {
        String message = ex.getMessage();
        Map<String, Object> map = new HashMap<>();
        if (ex instanceof UndeclaredThrowableException) {
            message = ex.getCause().getMessage();
            if (ex.getCause() instanceof InvalidAuthException) {
                map.put("status", HttpStatus.SC_FORBIDDEN);
            }
        }
        map.put("message", message);
        return map;
    }

}
