package org.openiam.selfsrvc.reports;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openiam.idm.srvc.report.dto.ReportInfoDto;
import org.openiam.idm.srvc.report.dto.ReportSubscriptionDto;
import org.openiam.idm.srvc.report.ws.GetAllReportsResponse;
import org.openiam.idm.srvc.report.ws.GetAllSubscribedReportsResponse;
import org.openiam.idm.srvc.report.ws.GetReportInfoResponse;
import org.openiam.idm.srvc.report.ws.WebReportService;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class ReportController extends SimpleFormController {

    private WebReportService reportService;

    public ReportController() {
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	ReportCommand reportCommand = new ReportCommand();
        if(!request.getParameterMap().containsKey("report.reportId")) {
        	HttpSession session = request.getSession(true);
    	 	session.removeAttribute("reportCommand");
            GetAllSubscribedReportsResponse allReportsResponse = reportService.getSubscribedReports();
            List<ReportSubscriptionDto> reports = (allReportsResponse != null && allReportsResponse.getReports() != null) ? allReportsResponse.getReports() : Collections.EMPTY_LIST;
            reportCommand.setRepors(reports);
        }
        return reportCommand;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response, Object command, BindException errors)
            throws Exception {
    	ReportCommand reportCommand1 = (ReportCommand) command;
        ReportSubscriptionDto selectedReport = reportCommand1.getReport();
        if (request.getParameterMap().containsKey("open_btn")) {
        	GetReportInfoResponse getReportInfoResponse = reportService.getReportByName(selectedReport.getReportName());
        	ReportInfoDto report = (getReportInfoResponse != null && getReportInfoResponse.getReport() != null) ? getReportInfoResponse.getReport() : (new ReportInfoDto());
            String reportLocation = report.getReportUrl();
            StringBuilder reportViewerLink = new StringBuilder();
            String requestURL = request.getRequestURL().toString();
            String reportViewerUrl = requestURL.substring(0,requestURL.indexOf(request.getContextPath()));
            reportViewerLink.append(reportViewerUrl).append("/reportviewer");
            reportViewerLink.append("/frameset?");
            reportViewerLink.append("__report=").append(reportLocation);
            response.sendRedirect(reportViewerLink.toString());
            return null;
        } else if (request.getParameterMap().containsKey("edit_btn")) {
        	SubscribeReportsCommand reportCommand = new SubscribeReportsCommand();
            reportCommand.setReport(selectedReport);
            return new ModelAndView(getSuccessView(), "reportCommand", reportCommand);
        } else if (request.getParameterMap().containsKey("add_btn")) {
        	SubscribeReportsCommand reportCommand = new SubscribeReportsCommand();
            reportCommand.setReport(new ReportSubscriptionDto());
            return new ModelAndView(getSuccessView(), "reportCommand", reportCommand);
        }
        return null;
    }

    public WebReportService getReportService() {
        return reportService;
    }

    public void setReportService(WebReportService reportService) {
        this.reportService = reportService;
    }
    
	protected Map referenceData(HttpServletRequest request) throws Exception {

		Map referenceData = new HashMap();

		Map<String, String> deliveryMethod = new LinkedHashMap<String, String>();
		deliveryMethod.put("EMAIL", "Email");
		deliveryMethod.put("VIEW", "View in SelfService");
		referenceData.put("deliveryMethodList", deliveryMethod);
		Map<String, String> deliveryFormat = new LinkedHashMap<String, String>();
		deliveryFormat.put("HTML", "Html");
		deliveryFormat.put("PDF", "Pdf");
		referenceData.put("deliveryFormatList", deliveryFormat);
		Map<String, String> deliveryAudience = new LinkedHashMap<String, String>();
		deliveryAudience.put("ROLE", "Role");
		deliveryAudience.put("DEPT", "Department");
		deliveryAudience.put("ORGANIZATION", "Organization");
		deliveryAudience.put("DIVISION", "Division");
		deliveryAudience.put("GROUP", "Group");
		referenceData.put("deliveryAudienceList", deliveryAudience);
		Map<String, String> status = new LinkedHashMap<String, String>();
		status.put("ACTIVE", "Active");
		status.put("DISABLED", "Disabled");
		referenceData.put("statusList", status);
		Map<String, String> reports = new LinkedHashMap<String, String>();
		GetAllReportsResponse allReportsResponse = reportService.getReports();
		List<ReportInfoDto> reportsList = (allReportsResponse != null && allReportsResponse
				.getReports() != null) ? allReportsResponse.getReports()
				: Collections.EMPTY_LIST;
		for (ReportInfoDto reportSubscriptionDto : reportsList) {
			reports.put(reportSubscriptionDto.getReportName(),
					reportSubscriptionDto.getReportName());
		}
		referenceData.put("reportsList", reports);
		return referenceData;
	}
    
}