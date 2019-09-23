package io.soffa.core.persistence;

import javax.persistence.*;

@MappedSuperclass
public abstract class EntityWithVersion<I extends EntityId> extends AbstractEntity<I> {

    @Version
    @Column(name = "_version")
    private Integer dbVersion = 0;

    public Integer getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(Integer dbVersion) {
        this.dbVersion = dbVersion;
    }
}


