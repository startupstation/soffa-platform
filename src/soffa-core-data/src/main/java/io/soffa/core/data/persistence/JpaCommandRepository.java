package io.soffa.core.data.persistence;


import javax.persistence.EntityManager;

public class JpaCommandRepository<E extends IEntity, I> implements EntityCommandRepository<E, I> {

    private EntityManagerProxy emProvider;

    public JpaCommandRepository(EntityManagerProxy emProvider) {
        this.emProvider = emProvider;
    }

    public JpaCommandRepository(EntityManager em) {
        this.emProvider = new SimpleEntityManagerProxy(em);
    }

    @Override
    public E save(E entity) {
        if (emProvider.get().contains(entity)) {
            emProvider.get().merge(entity);
        } else {
            emProvider.get().persist(entity);
        }
        return entity;
    }

    @Override
    public E update(E entity) {
        return emProvider.get().merge(entity);
    }

    @Override
    public Iterable<E> saveAll(Iterable<E> entities) {
        entities.forEach(this::save);
        return entities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(E entity) {
        if (entity != null) {
            emProvider.get().remove(entity);
        }
    }


}
