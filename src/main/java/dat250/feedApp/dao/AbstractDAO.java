package dat250.feedApp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class AbstractDAO<T, K> implements GenericDAO<T, K> {


    protected EntityManager em;


    public AbstractDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void persist(T entity) {
        em.persist(entity);
    }

    @Override
    public void remove(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public T find(K id) {
        return em.find(getEntityClass(), id);
    }

    @Override
    public List<T> findAll() {
        TypedQuery<T> query = em.createQuery("SELECT obj FROM " + getEntityClass().getSimpleName() + " obj", getEntityClass());
        return query.getResultList();
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    protected abstract Class<T> getEntityClass();
}

