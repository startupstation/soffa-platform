package io.soffa.core.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Paging {

    private int page;
    private int size;
    private String sort;

    public Paging(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public Paging(int page, int size, String sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }
}
