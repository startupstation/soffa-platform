package io.soffa.core.data.persistence;

import io.soffa.comons.core.Reflection;
import io.soffa.comons.core.exception.FunctionalException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaRepository<E extends IEntity, I> implements EntityRepository<E, I> {

    private JpaCommandRepository<E,I> commands;
    private JpaQueryRepository<E,I> queries;

    @SuppressWarnings("unchecked")
    public JpaRepository(EntityManagerProxy emProvider) {
        this.commands = new JpaCommandRepository<>(emProvider);
        this.queries = new JpaQueryRepository<>(emProvider);
    }

    @SuppressWarnings("unchecked")
    public JpaRepository(EntityManager em) {
        this.commands = new JpaCommandRepository<>(em);
        Class<E> entityClass = (Class<E>) Reflection.fndGenericSuperClass(this, 0)
                .orElseThrow(() -> new FunctionalException("Repository is out of its bounds (Missing IEntity): " + this.getClass()));
        this.queries = new JpaQueryRepository<E, I>(em, entityClass);
    }


    @Override
    public E save(E entity) {
        return commands.save(entity);
    }

    @Override
    public E update(E entity) {
        return commands.update(entity);
    }

    @Override
    public Iterable<E> saveAll(Iterable<E> entities) {
        return commands.saveAll(entities);
    }

    @Override
    public void delete(E entity) {
        commands.delete(entity);
    }

    @Override
    public Optional<E> findById(I id) {
        return queries.findById(id);
    }

    @Override
    public boolean existsById(I id) {
        return queries.existsById(id);
    }

    @Override
    public Iterable<E> findAll() {
        return queries.findAll();
    }

    @Override
    public Iterable<E> findAllById(Iterable<I> ids) {
        return queries.findAllById(ids);
    }

    @Override
    public List<E> findByQuery(String where, Object... args) {
        return queries.findByQuery(where, args);
    }

    @Override
    public long count() {
        return queries.count();
    }

    @Override
    public Long countByQuery(String where, Object... args) {
        return queries.countByQuery(where, args);
    }

    @Override
    public boolean isEmpty() {
        return queries.isEmpty();
    }

    @Override
    public <T> List<T> findByQuery(Class<T> resultType, String query, Object... args) {
        return queries.findByQuery(resultType, query, args);
    }
}
