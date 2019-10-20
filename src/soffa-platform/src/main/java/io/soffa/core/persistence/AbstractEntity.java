package io.soffa.core.persistence;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity<I extends EntityId> implements IEntity {

    @Id
    @Embedded
    private I id;

    @Version
    @Column(name = "_version")
    private Integer dbVersion = 0;

    public AbstractEntity() {
    }

    public AbstractEntity(I id) {
        this.id = id;
    }

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    public Integer getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(Integer dbVersion) {
        this.dbVersion = dbVersion;
    }
}


