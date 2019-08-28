package io.soffa.core.data.persistence;

import javax.persistence.EntityManager;

public class SimpleEntityManagerProxy implements EntityManagerProxy {

    private EntityManager entityManager;

    SimpleEntityManagerProxy(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager get() {
        return entityManager;
    }
}