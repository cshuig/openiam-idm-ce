package org.openiam.ui.webconsole.idm.web.mvc.provisioning.logviewer;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.mule.util.StringUtils;
import org.openiam.idm.searchbeans.AuditLogSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.audit.ws.IdmAuditLogWebDataService;
import org.openiam.idm.srvc.report.dto.ReportQueryDto;
import org.openiam.idm.srvc.report.ws.ReportWebService;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Controller
public class SynchReportController extends AbstractController {

    @Resource(name = "reportServiceClient")
    private ReportWebService reportServiceClient;

    @Resource(name = "auditServiceClient")
    private IdmAuditLogWebDataService auditServiceClient;

    @Value("${org.openiam.ui.idm.synchronization.edit.root.name}")
    private String rootMenuName;

    @RequestMapping(value = "/provisioning/synchronization-report", method = RequestMethod.GET)
    public String getReportForm(@RequestParam(value = "id", required = true) String synchronizationId,
            final HttpServletRequest request, Model model) {

        setMenuTree(request, rootMenuName);

        SynchReportCommand reportCommand = new SynchReportCommand();
        reportCommand.setSynchConfigId(synchronizationId);
        reportCommand.setAuditLogDate(new Date());
        model.addAttribute("reportCommand", reportCommand);
        model.addAttribute("dateFormatDP", DateFormatStr.getDpDate());
        return "provisioning/logviewer/reportParameters";
    }

    @RequestMapping(value = "/provisioning/synchronization-report", method = RequestMethod.POST)
    public String getReport(final HttpServletRequest request, final @RequestBody SynchReportCommand reportCommand) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse(500);
        if (StringUtils.isEmpty(reportCommand.getSynchConfigId())) {
            ajaxResponse.addError(new ErrorToken(Errors.INVALID_REQUEST));
        } else {
            reportCommand.setDatesFromStr();
            ReportQueryDto reportQuery = new ReportQueryDto();
            reportQuery.setReportName("SYNCHRONIZATION_REPORT");
            reportQuery.addParameterValue("CONFIG_ID", reportCommand.getSynchConfigId());
            if (StringUtils.isNotEmpty(reportCommand.getAuditLogId())) {
                reportQuery.addParameterValue("AUDIT_LOG_ID", reportCommand.getAuditLogId());
            }
            final String reportRunUri = reportServiceClient.getReportUrl(reportQuery, null,
                    "/reportviewer/", localeResolver.resolveLocale(request).toString());
            ajaxResponse.setRedirectURL(reportRunUri);
            ajaxResponse.setStatus(200);
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisioning/synchronization-items", method = RequestMethod.POST)
    public String getSynchItems(final HttpServletRequest request, final @RequestBody SynchReportCommand reportCommand) {

        final int MAX_RESULT_SIZE = 100;
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse(500);

        if (StringUtils.isNotBlank(reportCommand.getSynchConfigId())) {
            reportCommand.setDatesFromStr();
            AuditLogSearchBean searchBean = new AuditLogSearchBean();
            searchBean.setAction(AuditAction.SYNCHRONIZATION.value());
            searchBean.setSource(reportCommand.getSynchConfigId());
            final DateTime dateTime = new DateTime(reportCommand.getAuditLogDate()).withTime(0, 0, 0, 0);
            searchBean.setFrom(dateTime.toDate());
            searchBean.setTo(dateTime.plusDays(1).toDate());

            final List<IdmAuditLog> auditLogs = auditServiceClient.findBeans(searchBean, 0, MAX_RESULT_SIZE);
            if (CollectionUtils.isNotEmpty(auditLogs)) {
                for (IdmAuditLog entity : auditLogs) {
                    ajaxResponse.addContextValues(entity.getId(), entity.getTimestamp().getTime());
                }
                ajaxResponse.setStatus(200);
            }
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @ModelAttribute("formatList")
    public Map<String, String> populateFormatList() {
        Map<String, String> items = new LinkedHashMap<String, String>();
        items.put("HTML", "HTML");
        return items;
    }
}
