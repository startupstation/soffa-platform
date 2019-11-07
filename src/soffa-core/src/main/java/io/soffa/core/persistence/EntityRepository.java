package io.soffa.core.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface EntityRepository<T extends AbstractEntity<I>, I extends EntityId> {

    boolean isEmpty();

    T save(T entity);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    void delete(T entity);

    void deleteAll(Iterable<T> entity);

    void deleteById(I id);

    boolean existsById(I id);

    long count();

    long countBy(String property, Serializable value);

    Optional<T> findById(I id);

    List<T> findAll();

}
