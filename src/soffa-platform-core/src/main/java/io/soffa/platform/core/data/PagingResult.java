package io.soffa.platform.core.data;

import lombok.Value;

import java.util.List;

@Value
public class PagingResult<T> {

    List<T> content;
    long totalItems;
    int totalPages;
    boolean hasMoreItems;

}
