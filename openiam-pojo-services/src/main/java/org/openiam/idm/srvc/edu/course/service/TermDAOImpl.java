package org.openiam.idm.srvc.edu.course.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openiam.idm.srvc.edu.course.domain.TermEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.List;

/**
 * Data access object implementation for Course.
 */
public class TermDAOImpl implements TermDAO {

    private static final Log log = LogFactory.getLog(TermDAOImpl.class);

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


    public TermEntity findById(String id) {
        try {
            TermEntity instance = (TermEntity) sessionFactory
                    .getCurrentSession().get(TermEntity.class, id);
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
    public void add(TermEntity program) {
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
    public void remove(TermEntity instance) {
        log.debug("deleting Address instance");
        try {
            sessionFactory.getCurrentSession().delete(instance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public void update(TermEntity instance) {
        log.debug("merging Organization instance");
        try {
            sessionFactory.getCurrentSession().merge(instance);
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @Override
    public List<TermEntity> getTermsByDistrict(String districtId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(TermEntity.class)
                .add(Restrictions.eq("districtId", districtId))
                .addOrder(Order.asc("name"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<TermEntity> results = (List<TermEntity>)criteria.list();
        return results;

    }

    public boolean hasCourses(String termId) {

        System.out.println("Searching for courses linked to a term");


        String sql = "SELECT count(COURSE_ID) FROM openiam.COURSE_TERM WHERE TERM_ID = ?" ;


        Integer total = jdbcTemplateObject.queryForObject(sql, new Object[] {termId}, Integer.class);

        if (total > 0) {
            return true;
        }

        return false;




    }




    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);

    }
}
