package org.openiam.idm.srvc.edu.course.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.mule.util.StringUtils;
import org.openiam.idm.srvc.edu.course.dto.Course;
import org.openiam.idm.srvc.edu.course.dto.CourseSearch;
import org.openiam.idm.srvc.edu.course.dto.CourseSearchResult;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object implementation for Course.
 */
public class CourseDAOImpl implements CourseDAO {

    private static final Log log = LogFactory.getLog(CourseDAOImpl.class);

    private SessionFactory sessionFactory;
    private JdbcTemplate jdbcTemplateObject;
    private DataSource dataSource;



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


    public Course findById(String id) {
        try {
            Course instance = (Course) sessionFactory
                    .getCurrentSession().get(Course.class, id);
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
    public Course add(Course program) {
        log.debug("persisting Program instance");
        try {

            Session session = sessionFactory.getCurrentSession();
            session.persist(program);

            log.debug("persist successful");
            return program;

        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }

    }

    @Override
    public void remove(Course instance) {
        log.debug("deleting Address instance");
        try {
            sessionFactory.getCurrentSession().delete(instance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Course update(Course instance) {
        log.debug("merging Organization instance");
        try {
            return (Course) sessionFactory.getCurrentSession().merge(instance);
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @Override
    public List<Course> getAllCourses() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Course.class)
                .addOrder(Order.asc("name"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<Course> results = (List<Course>)criteria.list();
        return results;
    }

    @Override
    public List<CourseSearchResult>  searchCourses(CourseSearch search) {

        System.out.println("Searching for courses...in dao");


        StringBuilder sql = new StringBuilder("SELECT COURSE_ID, NAME, COURSE_NUMBER, DISTRICT_NAME, SCHOOL_NAME, TERM_NAME, SECTION_NBR, " +
                " COURSE_TERM_ID, DISTRICT_ID, SCHOOL_ID " +
                " FROM course_summary_vw ") ;

        List<Object> queryParam = new ArrayList<Object>();

        if (StringUtils.isNotEmpty(search.getDistrictId())) {
            sql.append(" WHERE DISTRICT_ID = ? " );
            queryParam.add(search.getDistrictId());
        }

        if (StringUtils.isNotEmpty(search.getSchoolId())) {
            sql.append(" WHERE SCHOOL_ID = ? " );
            queryParam.add(search.getSchoolId());
        }



        List<CourseSearchResult> result  = jdbcTemplateObject.query(sql.toString(),
                queryParam.toArray() ,
                new BeanPropertyRowMapper(CourseSearchResult.class));


        return result;

    }

    public CourseSearchResult getCourseByTerm(String courseId, String termId) {
        StringBuilder sql = new StringBuilder("SELECT COURSE_ID, NAME, COURSE_NUMBER, DISTRICT_NAME, SCHOOL_NAME, TERM_NAME, SECTION_NBR, " +
                " COURSE_TERM_ID, DISTRICT_ID, SCHOOL_ID " +
                " FROM course_summary_vw " +
                " WHERE COURSE_ID = ? AND COURSE_TERM_ID = ? ") ;

        List<Object> queryParam = new ArrayList<Object>();
        queryParam.add(courseId);
        queryParam.add(termId);


        CourseSearchResult result  = (CourseSearchResult)jdbcTemplateObject.queryForObject (sql.toString(),
                queryParam.toArray() ,
                new BeanPropertyRowMapper(CourseSearchResult.class));

        return result;


    }



    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);

    }
}
