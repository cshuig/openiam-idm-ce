package org.openiam.idm.srvc.edu.course.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openiam.idm.srvc.edu.course.domain.CourseTermEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Data access object implementation for Course.
 */
public class CourseTermDAOImpl implements CourseTermDAO {

    private static final Log log = LogFactory.getLog(CourseTermDAOImpl.class);

    private SessionFactory sessionFactory;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;



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


    public CourseTermEntity findById(String id) {
        try {
            CourseTermEntity instance = (CourseTermEntity) sessionFactory
                    .getCurrentSession().get(CourseTermEntity.class, id);
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
    public void add(CourseTermEntity program) {
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
    public void remove(CourseTermEntity instance) {
        log.debug("deleting Address instance");
        try {
            sessionFactory.getCurrentSession().delete(instance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public void update(CourseTermEntity instance) {
        log.debug("merging Organization instance");
        try {
            sessionFactory.getCurrentSession().merge(instance);
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }








    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);

    }
}
