package org.openiam.idm.srvc.report.service;

import org.openiam.idm.srvc.report.domain.ReportSubCriteriaParamEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.Test;

/**
 * Smoke Test for DAO service for ReportSubCriteriaParamEntity
 *
 * @author ekta.agarwal
 */
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml","classpath:dozer-application-context-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ReportSubCriteriaParamDAOTouchTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    private ReportSubCriteriaParamDao criteriaParamDao;

    @Test
    public void touchSave() {
        criteriaParamDao.save(new ReportSubCriteriaParamEntity());
    }

    @Test
    public void touchFindByReportInfoId() {
        criteriaParamDao.findByReportInfoId("1");
    }
}
