package io.soffa.core.data.persistence;

public interface EntityRepository<E extends IEntity, I> extends EntityQueryRepository<E, I>, EntityCommandRepository<E, I> {

}
