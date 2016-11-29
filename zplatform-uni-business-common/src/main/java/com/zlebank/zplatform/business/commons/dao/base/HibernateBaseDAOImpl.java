/* 
 * BaseHibernateImpl.java  
 * 
 * version TODO
 *
 * 2015-6-25 
 * 
 * Copyright (c) 2015,.All rights reserved.
 * 
 */
package com.zlebank.zplatform.business.commons.dao.base;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.dao.BaseDAO;

/**
 * hibernate数据库连接实现类，里面有SessionFactory，可以取到Session
 * 
 * @author guojia
 * @version
 * @date 2015-6-25 下午05:28:34
 * @since
 */
public class HibernateBaseDAOImpl<T> implements BaseDAO<T> {
	 private static final Log log = LogFactory.getLog(HibernateBaseDAOImpl.class);
	 public  static final String ENCODING = "UTF-8";
    @Autowired
    private SessionFactory sessionFactory = null;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public T merge(T t) {
        return (T) getSession().merge(t.getClass().getSimpleName(), t);
    }
    public void saveEntity(T t) {
          getSession().save(t);
    }
    public T update(T t) {
        getSession().update(t);
        return t;
    }
    public List<?> queryByHQL(String queryString,Object[] paramaters ) {
        List<?> result = null;
        try {
            log.info("queryString:"+queryString);
            Query query = getSession().createQuery(queryString);
            if (paramaters != null) {
                for (int i = 0; i < paramaters.length; i++) {
                    query.setParameter(i, paramaters[i]);
                    log.info("paramaters "+i+":"+paramaters[i]);
                }
            }
            result = query.list();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public List<?> queryByHQL(String queryString, String paramaterName,
            Collection<?> paramater) {
        log.info("queryString is " + queryString);
        Query query = null;
        List<?> result = null;
        try {
            query = getSession().createQuery(queryString);
            query.setParameterList(paramaterName, paramater);
            result = query.list();
           
        } catch (HibernateException e) {
            log.error("query error ", e);
            throw e;
        }
        return result;
    }
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public List<?> queryBySQL(String queryString,Object[] paramaters) {
        List<?> resultList = null;
        try {
            Session session = getSession();
            SQLQuery query = (SQLQuery) session.createSQLQuery(queryString)
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            if (null != paramaters) {
                for (int i = 0; i < paramaters.length; i++) {
                    query.setParameter(i, paramaters[i]);
                }
            }
            resultList = query.list();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return resultList;
    }

    public List<?> queryBySQL(String queryString, String paramaterName,
            Collection<?> paramater) {
        log.info("queryString is " + queryString);
        SQLQuery query = null;
        List<?> resultList = null;
        try {
            Session session = getSession();
            query = (SQLQuery) session.createSQLQuery(queryString)
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            query.setParameterList(paramaterName, paramater);
            resultList = query.list();
           
        } catch (HibernateException e) {
            log.error("sql query error", e);
            throw e;
        }
        return resultList;
    }

    
   


}
