package com.nidhi.cms.service.impl;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractDataSourceDao {

    @Autowired
    protected EntityManager entityManager;

    protected Session getHibernateSession() {
        return entityManager.unwrap(Session.class);
    }

    @SuppressWarnings("deprecation")
    protected <T> Object getSingleResult(final NativeQuery<?> query, final Class<T> targetClass) {
        return query.setResultTransformer(Transformers.aliasToBean(targetClass)).setMaxResults(1).getSingleResult();
    }
    
    @SuppressWarnings({ "deprecation", "unchecked" })
    protected <T> List<T> getResultList(final NativeQuery<?> query, final Class<T> targetClass) {
        return (List<T>) query.setResultTransformer(Transformers.aliasToBean(targetClass)).getResultList();
    }
    
    protected int getCount(final NativeQuery<?> query) {
        return ((BigInteger) query.getSingleResult()).intValue();
    }

}
