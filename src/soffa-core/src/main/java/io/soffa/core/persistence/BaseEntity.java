package io.soffa.core.persistence;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity<I extends EntityId> extends AbstractEntity<I> {

    public BaseEntity() {
    }

    public BaseEntity(I id) {
        super(id);
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onPrePersist() {
        createdAt = new Date();
    }
}


