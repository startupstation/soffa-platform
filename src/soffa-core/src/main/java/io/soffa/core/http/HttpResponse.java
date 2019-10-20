package io.soffa.core.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {

    int status;
    String body;
    Map<String, String> headers;

    public boolean isSuccess() {
        return status >= 200 && status < 300;
    }

}
