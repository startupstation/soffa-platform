package io.soffa.platform.core.http;

import io.soffa.platform.core.data.JSON;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.Data;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Data
public class HttpRequest {

    private String method = "POST";
    private String url;
    private Object data;
    private int timeout = 10000;
    private Map<String, String> headers = new HashMap<>();

    private static RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
        .handle(UnirestException.class)
        .withDelay(Duration.ofSeconds(1))
        .withMaxRetries(3);

    static {

        Unirest.config()
            .automaticRetries(true)
            .setObjectMapper(new ObjectMapper() {
                @Override
                public <T> T readValue(String value, Class<T> valueType) {
                    return JSON.deserialize(value, valueType);
                }

                @Override
                public String writeValue(Object value) {
                    if (value instanceof String) {
                        return (String) value;
                    }
                    return JSON.serializeSafe(value);
                }
            });
    }

    private HttpRequest() {
    }

    private HttpRequest(String method, String url) {
        this.method = method;
        this.url = url;
    }

    private HttpRequest(String method, String url, Object data) {
        this.method = method;
        this.url = url;
        this.data = data;
    }

    public HttpRequest withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public void setContentType(String value) {
        this.headers.put("Content-Type", value);
    }

    public static HttpRequest post(String url, Object data) {
        return new HttpRequest("POST", url, data);
    }

    public static HttpRequest get(String url) {
        return new HttpRequest("GET", url);
    }

    public HttpResponse execute() {
        HttpRequestWithBody req = Unirest.request(method, url).headers(headers);
        req.socketTimeout(timeout);
        kong.unirest.HttpResponse<String> response;

        if (method.equalsIgnoreCase("POST")) {
            response = Failsafe.with(retryPolicy).get(() -> req.body(data).asString());
        } else {
            response = Failsafe.with(retryPolicy).get(req::asString);
        }
        return new HttpResponse(response.getStatus(), response.getBody(), null);
    }

}
