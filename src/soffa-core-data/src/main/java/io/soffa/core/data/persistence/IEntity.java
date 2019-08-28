package io.soffa.core.data.persistence;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public interface IEntity extends Serializable {
}
