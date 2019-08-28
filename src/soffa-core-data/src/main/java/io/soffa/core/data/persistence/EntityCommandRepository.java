package io.soffa.core.data.persistence;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EntityCommandRepository<E extends IEntity, I> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    @Transactional
    E save(E entity);

    @Transactional
    E update(E entity);

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null}.
     * @return the saved entities will never be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Transactional
    Iterable<E> saveAll(Iterable<E> entities);

    /**
     * Deletes a given entity.
     *
     * @param entity TODO
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Transactional
    void delete(E entity);

}
