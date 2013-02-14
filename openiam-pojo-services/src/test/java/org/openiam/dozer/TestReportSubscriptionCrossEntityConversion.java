package org.openiam.dozer;

import org.apache.commons.lang.RandomStringUtils;
import org.openiam.dozer.converter.ReportSubscriptionDozerConverter;
import org.openiam.idm.srvc.report.domain.ReportSubscriptionEntity;
import org.openiam.idm.srvc.report.dto.ReportSubscriptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration("classpath:test-application-context.xml")
public class TestReportSubscriptionCrossEntityConversion extends AbstractTestNGSpringContextTests {

    @Autowired
    private ReportSubscriptionDozerConverter reportSubscriptionDozerMapper;

    @Test
    public void testConversion() {
        final ReportSubscriptionEntity entity = createSimpleEntity();
        final ReportSubscriptionDto resource = reportSubscriptionDozerMapper.convertToDTO(entity, false);
        confirmSimple(resource, entity);
        final ReportSubscriptionEntity convertedEntity = reportSubscriptionDozerMapper.convertToEntity(resource, false);
        confirmSimple(resource, convertedEntity);
    }

    private ReportSubscriptionEntity createSimpleEntity() {
        final ReportSubscriptionEntity entity = new ReportSubscriptionEntity();
        entity.setReportId(rs(4));
        entity.setReportName(rs(4));
        entity.setDeliveryMethod(rs(4));
        entity.setDeliveryFormat(rs(4));
        entity.setDeliveryAudience(rs(4));
        entity.setStatus(rs(4));
        entity.setUserId(rs(4));
        return entity;
    }

    private void confirmSimple(final ReportSubscriptionDto dto, final ReportSubscriptionEntity entity) {
        Assert.assertEquals(dto.getReportId(), entity.getReportId());
        Assert.assertEquals(dto.getDeliveryAudience(), entity.getDeliveryAudience());
        Assert.assertEquals(dto.getDeliveryFormat(), entity.getDeliveryFormat());
        Assert.assertEquals(dto.getDeliveryMethod(), entity.getDeliveryMethod());
        Assert.assertEquals(dto.getReportName(), entity.getReportName());
        Assert.assertEquals(dto.getStatus(), entity.getStatus());
        Assert.assertEquals(dto.getUserId(), entity.getUserId());
    }

    private String rs(final int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }
}
