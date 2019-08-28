package io.soffa.core.data.persistence;

import io.soffa.comons.core.Reflection;
import io.soffa.comons.core.exception.FunctionalException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class JpaQueryRepository<E extends IEntity, I> implements EntityQueryRepository<E, I> {

    private Class<E> entityClass;
    private String entityName;
    private EntityManagerProxy emProvider;

    @SuppressWarnings("unchecked")
    public JpaQueryRepository(EntityManagerProxy emProvider) {
        this.emProvider = emProvider;
        entityClass = (Class<E>) Reflection.fndGenericSuperClass(this, 0)
                .orElseThrow(() -> new FunctionalException("Repository is out of its bounds (Missing IEntity): " + this.getClass()));
        entityName = entityClass.getSimpleName();
    }

   public JpaQueryRepository(EntityManager em, Class<E> entityClass) {
        this.emProvider = new SimpleEntityManagerProxy(em);
        this.entityClass = entityClass;
        entityName = entityClass.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    public JpaQueryRepository(EntityManager em) {
        this.emProvider = new SimpleEntityManagerProxy(em);
        entityClass = (Class<E>) Reflection.fndGenericSuperClass(this, 0)
                .orElseThrow(() -> new FunctionalException("Repository is out of its bounds (Missin IEntity): " + this.getClass()));
        entityName = entityClass.getSimpleName();
    }

    @Override
    public Optional<E> findById(I id) {
        return Optional.ofNullable(emProvider.get().find(entityClass, id));
    }

    @Override
    public boolean existsById(I id) {
        Query query = emProvider.get().createQuery(String.format("SELECT COUNT(e) FROM %1s e WHERE id = :id", entityName));
        query.setParameter("id", id);
        return (long) query.getSingleResult() > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<E> findAll() {
        Query query = emProvider.get().createQuery(String.format("SELECT e FROM %1s", entityName), entityClass);
        return (List<E>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<E> findAllById(Iterable<I> ids) {
        Query query = emProvider.get().createQuery(String.format("SELECT e FROM %1s WHER id in :ids", entityName), entityClass);
        query.setParameter("ids", ids);
        return (List<E>) query.getResultList();
    }

    @Override
    public long count() {
        return emProvider.get().createQuery(String.format("SELECT COUNT(id) FROM %1s", entityName), Long.class).getSingleResult();
    }

    @Override
    public Long countByQuery(String where, Object... args) {
        String query = String.format("SELECT COUNT(id) FROM %s WHERE %s", entityName, where);
        TypedQuery<Long> q = emProvider.get().createQuery(query, Long.class);
        fillParams(q, args);
        return q.getSingleResult();
    }

    @Override
    public boolean isEmpty() {
        return count() == 0;
    }

    public List<E> findByQuery(String where, Object... args) {
        String query = String.format("FROM %s WHERE %s", entityName, where);
        TypedQuery<E> q = emProvider.get().createQuery(query, entityClass);
        fillParams(q, args);
        return  q.getResultList();
    }

    @Override
    public <T> List<T> findByQuery(Class<T> resultType, String query, Object... args) {
        TypedQuery<T> typedQuery = emProvider.get().createQuery(query, resultType);
        fillParams(typedQuery, args);
        return typedQuery.getResultList();
    }


    private void fillParams(Query query, Object... args) {
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }
        }
    }

}
