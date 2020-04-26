package io.soffa.platform.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@MappedSuperclass
public abstract class ValueModel implements Serializable {

    @Column(name = "id")
    private String value;

    @Override
    public String toString() {
        return value;
    }
}
