package io.soffa.adapters.spring.security;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends DefaultErrorAttributes {

    public Map<String, Object> getErrorAttributes(ServerRequest request, Boolean includeStackTrace) {
        Throwable ex = getError(request);
        String message = ex.getMessage();
        if (ex instanceof UndeclaredThrowableException) {
            message = ex.getCause().getMessage();
        }
        Map<String, Object> map = super.getErrorAttributes(request, includeStackTrace);
        // map["status"] = HttpStatus.BAD_REQUEST
        map.put("message", message);
        return map;
    }

}
