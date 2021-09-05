package ru.academits.nikolenko.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Transactional
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
    private static final Logger logger = LoggerFactory.getLogger(GenericDao.class);

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> clazz;

    public GenericDaoImpl(Class<T> type) {
        this.clazz = type;
    }

    @Transactional
    @Override
    public void create(T obj) {
        logger.info("Called method create in GenericDaoImpl");
        entityManager.persist(obj);
    }

    @Transactional
    @Override
    public void update(T obj) {
        logger.info("Called method update in GenericDaoImpl");
        entityManager.merge(obj);
    }

    @Transactional
    @Override
    public void remove(T obj) {
        logger.info("Called method remove in GenericDaoImpl");
        entityManager.remove(obj);
    }

    @Override
    public T getById(PK id) {
        logger.info("Called method getById in GenericDaoImpl");
        return entityManager.find(clazz, id);
    }

    @Transactional
    @Override
    public List<T> findAll() {
        logger.info("Called method findAll in GenericDaoImpl");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);

        Root<T> root = cq.from(clazz);

        CriteriaQuery<T> select = cq.select(root);
        TypedQuery<T> q = entityManager.createQuery(select);

        return q.getResultList();
    }
}
