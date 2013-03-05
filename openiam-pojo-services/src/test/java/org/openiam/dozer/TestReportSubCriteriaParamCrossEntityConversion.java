package org.openiam.dozer;

import org.apache.commons.lang.RandomStringUtils;
import org.openiam.dozer.converter.ReportSubCriteriaParamDozerConverter;
import org.openiam.idm.srvc.report.domain.ReportSubCriteriaParamEntity;
import org.openiam.idm.srvc.report.domain.ReportSubscriptionEntity;
import org.openiam.idm.srvc.report.dto.ReportSubCriteriaParamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration("classpath:test-application-context.xml")
public class TestReportSubCriteriaParamCrossEntityConversion extends AbstractTestNGSpringContextTests {

    @Autowired
    private ReportSubCriteriaParamDozerConverter criteriaParamDozerConverter;

    @Test
    public void testConversion() {
        final ReportSubCriteriaParamEntity entity = createSimpleEntity();
        final ReportSubCriteriaParamDto resource = criteriaParamDozerConverter.convertToDTO(entity, false);
        confirmSimple(resource, entity);
        final ReportSubCriteriaParamEntity convertedEntity = criteriaParamDozerConverter.convertToEntity(resource, false);
        confirmSimple(resource, convertedEntity);
    }

    private ReportSubCriteriaParamEntity createSimpleEntity() {
        final ReportSubCriteriaParamEntity entity = new ReportSubCriteriaParamEntity();
        entity.setId(rs(4));
        ReportSubscriptionEntity report = new ReportSubscriptionEntity();
        report.setReportId(rs(4));
        entity.setReport(report);
        entity.setName(rs(4));
        entity.setValue(rs(4));
        return entity;
    }

    private void confirmSimple(final ReportSubCriteriaParamDto dto, final ReportSubCriteriaParamEntity entity) {
        Assert.assertEquals(dto.getId(), entity.getId());
        Assert.assertEquals(dto.getName(), entity.getName());
        Assert.assertEquals(dto.getValue(), entity.getValue());
    }

    private String rs(final int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }
}
