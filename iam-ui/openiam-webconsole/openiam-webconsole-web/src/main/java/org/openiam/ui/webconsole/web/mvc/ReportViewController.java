package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.idm.srvc.org.dto.OrganizationType;
import org.openiam.idm.srvc.report.dto.ReportCriteriaParamDto;
import org.openiam.idm.srvc.report.dto.ReportInfoDto;
import org.openiam.idm.srvc.report.dto.ReportQueryDto;
import org.openiam.idm.srvc.report.ws.ReportWebService;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.web.util.DateFormatStr;
import org.openiam.ui.web.validator.ReportRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class ReportViewController extends AbstractController {

    @Autowired
    ReportRequestValidator reportRequestValidator;

    @Resource(name = "reportServiceClient")
    private ReportWebService reportServiceClient;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(reportRequestValidator);
    }

    @RequestMapping(value = "/viewReport", method = RequestMethod.GET)
    public String viewReport(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestParam(required = false, value = "id") String id) throws IOException {
        if (StringUtils.isNotBlank(id)) {
            ReportInfoDto reportInfo = reportServiceClient.getReport(id).getReport();
            if (reportInfo == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            if (CollectionUtils.isNotEmpty(reportInfo.getReportParams())) {
                request.setAttribute("reportInfo", reportInfo);
                request.setAttribute("reportParameters", getOrderedParameters(reportInfo.getReportParams()));
                request.setAttribute("managedSystems", getManagedSystemsAsKeyNameBeans());
                request.setAttribute("resourceTypes", getResourceTypesAsKeyNameBeans());
                request.setAttribute("userStatuses", getUserStatusList());
                request.setAttribute("secondaryStatuses", getUserSecondaryStatusList());
				request.setAttribute("riskList", getResourceRiskAsKeyNameBeans());
				request.setAttribute("reportAsJSON", jacksonMapper.writeValueAsString(reportInfo));
                request.setAttribute("dateFormatDP", DateFormatStr.getDpDate());
                final List<List<OrganizationType>> orgTypes = getOrgTypeList();
                request.setAttribute("orgHierarchy", (orgTypes != null) ? jacksonMapper.writeValueAsString(orgTypes) : null);
                return "jar:report/parameters";
            } else {
                ReportQueryDto reportQuery = new ReportQueryDto();
                reportQuery.setReportName(reportInfo.getReportName());
                final String url = reportServiceClient.getReportUrl(reportQuery, null,
                        "/reportviewer/", localeResolver.resolveLocale(request).toString());
                return url != null ? "redirect:" + url : null;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    @RequestMapping(value = "/viewReport", method = RequestMethod.POST)
    public String generateReportUrl(final HttpServletRequest request, final HttpServletResponse response,
            final @Valid @RequestBody ReportInfoDto reportInfo) throws IOException {
        ReportQueryDto reportQuery = getReportQueryDto(reportInfo);
        final String url = reportServiceClient.getReportUrl(reportQuery, null,
                "/reportviewer/", localeResolver.resolveLocale(request).toString());
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        if (url != null) {
            ajaxResponse.setRedirectURL(url);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.REPORT_PARAMETERS_ACCEPTED));
            ajaxResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    private ReportQueryDto getReportQueryDto(final ReportInfoDto reportInfo) {
        ReportQueryDto reportQuery = new ReportQueryDto();
        reportQuery.setReportName(reportInfo.getReportName());
        for (ReportCriteriaParamDto parameter : reportInfo.getReportParams()) {
            reportQuery.addParameterValue(parameter.getName(), parameter.getValue());
        }
        return reportQuery;
    }

    private List<ReportCriteriaParamDto> getOrderedParameters(Collection<ReportCriteriaParamDto> params) {
        List<ReportCriteriaParamDto> orderedParams = new ArrayList<>(params);
        Collections.sort(orderedParams, new Comparator<ReportCriteriaParamDto>() {
            @Override
			public int compare(ReportCriteriaParamDto o1, ReportCriteriaParamDto o2) {
				return o1.getDisplayOrder().compareTo(o2.getDisplayOrder());
			}
        });
        return orderedParams;
    }
}
