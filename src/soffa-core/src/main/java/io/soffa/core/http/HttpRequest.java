package io.soffa.core.http;

import io.soffa.core.JSON;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class HttpRequest {

    private String method = "POST";
    private String url;
    private Object data;
    private int timeout = 5000;
    private Map<String, String> headers = new HashMap<>();

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
                        return (String)value;
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
            response = req.body(data).asString();
        } else {
            response = req.asString();
        }
        return new HttpResponse(response.getStatus(), response.getBody(), null);
    }

}
