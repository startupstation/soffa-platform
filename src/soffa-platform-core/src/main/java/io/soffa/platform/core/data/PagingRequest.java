package io.soffa.platform.core.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagingRequest {

    private int page;
    private int size;
    private String sort;

    public PagingRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PagingRequest(int page, int size, String sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }
}
