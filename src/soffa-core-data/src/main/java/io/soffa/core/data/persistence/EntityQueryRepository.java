package io.soffa.core.data.persistence;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EntityQueryRepository<E extends IEntity, I> {

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    @Transactional(readOnly = true)
    Optional<E> findById(I id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    @Transactional(readOnly = true)
    boolean existsById(I id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    @Transactional(readOnly = true)
    Iterable<E> findAll();

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids TODO
     * @return TODO
     */
    @Transactional(readOnly = true)
    Iterable<E> findAllById(Iterable<I> ids);

    /**
     * Returns all instances of the type with the given parameters.
     *
     * @param where TODO
     * @param args TODO
     * @return TODO
     */
    @Transactional(readOnly = true)
    List<E> findByQuery(String where, Object... args);


    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    @Transactional(readOnly = true)
    long count();

    /**
     * Returns the number of entities available ith the given parameters.
     *
     * @param where TODO
     * @param args TODO
     * @return TODO
     */
    @Transactional(readOnly = true)
    Long countByQuery(String where, Object... args);


    /**
     * Check if the repository is empty
     *
     * @return TODO
     */
    @Transactional(readOnly = true)
    boolean isEmpty();


    /**
     * Returns all instances of the result type with the given query.
     *
     * @param query TODO
     * @param resultType TODO
     * @param args TODO
     * @param <T> TODO
     * @return List of entities found
     */
    @Transactional(readOnly = true)
    <T> List<T> findByQuery(Class<T> resultType, String query, Object... args);

}
