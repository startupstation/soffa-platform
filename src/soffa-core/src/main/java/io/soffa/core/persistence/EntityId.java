package io.soffa.core.persistence;

import io.soffa.core.commons.IDs;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public abstract class EntityId implements Serializable {

    @Column(name = "id")
    private String value;

    public EntityId() {
        this.value = IDs.shortId();
    }

    public EntityId(String value) {
        if (value == null) {
            this.value = IDs.shortId();
        } else {
            this.value = value;
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityId entityId = (EntityId) o;
        return Objects.equals(value, entityId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
