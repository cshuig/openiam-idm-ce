package org.openiam.idm.srvc.edu.course.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.openiam.idm.srvc.edu.course.domain.ProgramEntity;

import javax.naming.InitialContext;
import java.util.List;

/**
 * Data access object implementation for Program.
 */
public class ProgramDAOImpl implements ProgramDAO {

    private static final Log log = LogFactory.getLog(ProgramDAOImpl.class);

    private SessionFactory sessionFactory;


    public void setSessionFactory(SessionFactory session) {
        this.sessionFactory = session;
    }

    protected SessionFactory getSessionFactory() {
        try {
            return (SessionFactory) new InitialContext()
                    .lookup("SessionFactory");
        } catch (Exception e) {
            log.error("Could not locate SessionFactory in JNDI", e);
            throw new IllegalStateException(
                    "Could not locate SessionFactory in JNDI");
        }
    }


    public ProgramEntity findById(String id) {
        try {
            ProgramEntity instance = (ProgramEntity) sessionFactory
                    .getCurrentSession().get(ProgramEntity.class, id);
            if (instance == null) {
                log.debug("get successful, no instance found");
            } else {
                log.debug("get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }



    @Override
    public void add(ProgramEntity program) {
        log.debug("persisting Program instance");
        try {

            Session session = sessionFactory.getCurrentSession();
            session.persist(program);


        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }

    }

    @Override
    public void remove(ProgramEntity instance) {
        log.debug("deleting Address instance");
        try {
            sessionFactory.getCurrentSession().delete(instance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public void update(ProgramEntity instance) {
        log.debug("merging Organization instance");
        try {
            sessionFactory.getCurrentSession().merge(instance);
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @Override
    public List<ProgramEntity> getAllPrograms() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(ProgramEntity.class)
                .addOrder(Order.asc("name"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);;

        List<ProgramEntity> results = (List<ProgramEntity>)criteria.list();
        return results;
    }
}
