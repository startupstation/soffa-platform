package io.soffa.core.data.persistence;

import javax.persistence.EntityManager;

public interface EntityManagerProxy {

    EntityManager get();

}