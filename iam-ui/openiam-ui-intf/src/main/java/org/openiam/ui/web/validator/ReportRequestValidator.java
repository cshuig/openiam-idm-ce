package org.openiam.ui.web.validator;

import org.apache.commons.lang.StringUtils;
import org.openiam.idm.srvc.report.dto.ReportCriteriaParamDto;
import org.openiam.idm.srvc.report.dto.ReportInfoDto;
import org.openiam.idm.srvc.report.ws.ReportWebService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReportRequestValidator implements Validator {

    @Resource(name = "reportServiceClient")
    private ReportWebService reportServiceClient;

    @Override
    public boolean supports(Class<?> clazz) {
        return ReportInfoDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReportInfoDto report = (ReportInfoDto) target;
        if (StringUtils.isEmpty(report.getReportName())) {
            errors.rejectValue("reportName", "required",
                    org.openiam.ui.util.messages.Errors.INVALID_REPORT_NAME
                            .getMessageName());
        }

        Map<String, Integer> frequency = new HashMap<String, Integer>();
        for(ReportCriteriaParamDto parameter : report.getReportParams()) {
            final String name = parameter.getName();
            if( !frequency.containsKey( name ) ){
                frequency.put( name, 1 );
            } else {
                frequency.put( name, frequency.get( name ) + 1 );
            }
        }

        ReportInfoDto reportInfo = reportServiceClient.getReportByName(report.getReportName()).getReport();
        for(ReportCriteriaParamDto parameterInfo : reportInfo.getReportParams()) {
            final String name = parameterInfo.getName();
            final int count = frequency.containsKey(name) ? frequency.get(name) : 0;
            if (parameterInfo.getIsRequired() && count == 0) {
                errors.rejectValue("reportParams", "required",
                        new String[] {parameterInfo.getCaption()},
                        org.openiam.ui.util.messages.Errors.REPORT_PARAMETER_IS_REQUIRED
                                .getMessageName());
            } else if (!parameterInfo.getIsMultiple() && count > 1) {
                errors.rejectValue("reportParams", "required",
                        new String[] {parameterInfo.getCaption()},
                        org.openiam.ui.util.messages.Errors.REPORT_PARAMETER_IS_NOT_MULTIPLE
                                .getMessageName());
            }
        }
    }
}
