package io.soffa.adapters.spring.data;

import io.soffa.core.Reflection;
import io.soffa.core.exception.TechnicalException;
import io.soffa.core.persistence.AbstractEntity;
import io.soffa.core.persistence.EntityId;
import io.soffa.core.persistence.EntityRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("ALL")
public class BaseJpaRepositoryAdapter<T extends AbstractEntity<I>, I extends EntityId> implements EntityRepository<T, I> {

    private Class<T> entityClass;
    private EntityManager em;
    protected SimpleJpaRepository<T, I> internalRepository;

    public BaseJpaRepositoryAdapter(Class<T> entityClass, EntityManager em) {
        this.entityClass = entityClass;
        this.em = em;
        this.internalRepository = new SimpleJpaRepository<>(entityClass, em);
    }

    public BaseJpaRepositoryAdapter(EntityManager em) {
        this.entityClass = (Class<T>) Reflection.fndGenericSuperClass(this, 0).orElseThrow(() -> new TechnicalException("GENERIC_TYPE_REQUIRED"));
        this.em = em;
        this.internalRepository = new SimpleJpaRepository<>(entityClass, em);
    }

    @Override
    public T save(T entity) {
        return internalRepository.save(entity);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return internalRepository.saveAll(entities);
    }

    @Override
    public void delete(T entity) {
        internalRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<T> entities) {
        internalRepository.deleteAll(entities);
    }

    @Override
    public void deleteById(I id) {
        internalRepository.deleteById(id);
    }

    @Override
    public long count() {
        return internalRepository.count();
    }

    @Override
    public Optional<T> findById(I id) {
        return internalRepository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return internalRepository.findAll();
    }

    public <E> List<E> query(Class<E> resultType, String query) {
        return em.createQuery(query, resultType).getResultList();
    }

    public <E> List<E> query(Class<E> resultType, String query, Map<String, Object> params) {
        Query q = em.createQuery(query, resultType);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return q.getResultList();
    }

    public List<T> query(String query) {
        return query(entityClass, query);
    }

    public List<T> query(String query, Map<String, Object> params) {
        return query(entityClass, query, params);
    }
}
