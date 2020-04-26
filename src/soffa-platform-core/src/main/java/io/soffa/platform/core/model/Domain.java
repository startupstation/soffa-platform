package io.soffa.platform.core.model;

import io.soffa.platform.core.annotations.AllOpen;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@AllOpen
@MappedSuperclass
public abstract class Domain<ID extends Serializable> {

    @Id
    private ID id;

    @Version
    @Column(name = "_version")
    private Integer domainVersion = 0;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Integer getDomainVersion() {
        return domainVersion;
    }

    public void setDomainVersion(Integer domainVersion) {
        this.domainVersion = domainVersion;
    }
}


