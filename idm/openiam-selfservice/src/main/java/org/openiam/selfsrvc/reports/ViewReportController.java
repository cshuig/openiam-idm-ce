package org.openiam.selfsrvc.reports;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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

public class ViewReportController extends SimpleFormController {

	private WebReportService reportService;
	private static ResourceBundle res = ResourceBundle
			.getBundle("securityconf");

	public ViewReportController() {
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		System.out.println("userId=" + userId);
		File[] files = getReportsListForUser(userId);
		ReportCommand reportCommand = new ReportCommand();
		reportCommand.setFiles(files);
		return reportCommand;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ReportCommand reportCommand1 = (ReportCommand) command;
		//ReportSubscriptionDto selectedReport = reportCommand1.getReport();
		if (request.getParameterMap().containsKey("open_btn")) {
			StringBuilder reportViewerLink = new StringBuilder();
			String requestURL = request.getRequestURL().toString();
			String reportViewerUrl = requestURL.substring(0,
					requestURL.indexOf(request.getContextPath()));
			reportViewerLink.append(reportViewerUrl).append("/reportviewer");
			reportViewerLink.append("/frameset?");
			reportViewerLink.append("__report=").append(requestURL);
			response.sendRedirect(reportViewerLink.toString());
			return null;
		} 
		return null;
	}


	private File[] getReportsListForUser(String userId) {
		File dir = new File(res.getString("reportRoot")
				+ "//GENERATED_REPORTS//VIEW//" + userId);
		File[] files = dir.listFiles();
		return files;
	}

	public WebReportService getReportService() {
		return reportService;
	}

	public void setReportService(WebReportService reportService) {
		this.reportService = reportService;
	}
}