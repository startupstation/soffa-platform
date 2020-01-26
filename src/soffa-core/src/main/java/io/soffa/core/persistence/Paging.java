package io.soffa.core.persistence;

import lombok.Value;

@Value
public class Paging {
    private int page;
    private int count;
}
