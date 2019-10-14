package io.soffa.adapters.spring.data;

import io.soffa.core.Reflection;
import io.soffa.core.exception.TechnicalException;
import io.soffa.core.persistence.AbstractEntity;
import io.soffa.core.persistence.EntityId;
import io.soffa.core.persistence.EntityRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    @Transactional
    public T save(T entity) {
        return internalRepository.save(entity);
    }

    @Override
    @Transactional
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return internalRepository.saveAll(entities);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        internalRepository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<T> entities) {
        internalRepository.deleteAll(entities);
    }

    @Override
    @Transactional
    public void deleteById(I id) {
        internalRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return internalRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmpty() {
        return count() == 0L;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(I id) {
        return internalRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return internalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public <E> List<E> query(Class<E> resultType, String query) {
        return em.createQuery(query, resultType).getResultList();
    }

    @Transactional(readOnly = true)
    public <E> List<E> query(Class<E> resultType, String query, Map<String, Object> params) {
        Query q = em.createQuery(query, resultType);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return q.getResultList();
    }

    @Transactional(readOnly = true)
    public List<T> query(String query) {
        return query(entityClass, query);
    }

    @Transactional(readOnly = true)
    public List<T> query(String query, Map<String, Object> params) {
        return query(entityClass, query, params);
    }

    @Transactional(readOnly = true)
    public Optional<T> querySingle(String query, Map<String, Object> params) {
        return querySingle(entityClass, query, params);
    }

    @Transactional(readOnly = true)
    public <E> Optional<E> querySingle(Class<E> resultType, String query, Map<String, Object> params) {
        Query q = em.createQuery(query, resultType);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        try {
            return Optional.of((E)q.getSingleResult());
        }catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
