package org.openiam.selfsrvc.reports;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cxf.common.util.StringUtils;
import org.openiam.idm.srvc.report.dto.ReportCriteriaParamDto;
import org.openiam.idm.srvc.report.dto.ReportSubCriteriaParamDto;
import org.openiam.idm.srvc.report.dto.ReportSubscriptionDto;
import org.openiam.idm.srvc.report.dto.ReportInfoDto;
import org.openiam.idm.srvc.report.ws.GetAllReportsResponse;
import org.openiam.idm.srvc.report.ws.WebReportService;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class SubscribeReportsController extends SimpleFormController {
	private static ResourceBundle res = ResourceBundle
			.getBundle("securityconf");

	private WebReportService reportService;

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		SubscribeReportsCommand reportCommand = ((SubscribeReportsCommand) command);
		if (request.getParameterMap().containsKey("save")) {
			if (!StringUtils.isEmpty(reportCommand.getReport().getReportName())) {
				List<ReportSubCriteriaParamDto> params = new LinkedList<ReportSubCriteriaParamDto>();
				if (reportCommand.getParamName() != null) {
					for (int i = 0; i < reportCommand.getParamName().length; i++) {
						String paramTypeId = reportCommand.getParamTypeId()[i];
						params.add(new ReportSubCriteriaParamDto(reportCommand
								.getReport().getReportId(), reportCommand
								.getParamName()[i], reportCommand
								.getParamValue()[i],
								reportCommand
								.getParamTypeId()[i]));
					}
				}
		        HttpSession session = request.getSession();
		        String userId = (String) session.getAttribute("userId");
				ReportSubscriptionDto dto = reportCommand.getReport();
				dto.setUserId(userId);
				reportService.createOrUpdateSubscribedReportInfo(
						reportCommand.getReport(), params);
			}
		}else{
			if (!StringUtils.isEmpty(reportCommand.getReport().getReportName())) {
				 ModelAndView modelAndView = new ModelAndView(getSuccessView(), "reportCommand", reportCommand);
		            List<ReportCriteriaParamDto> paramDtos = reportService.getReportParametersByReportName(reportCommand.getReport().getReportName()).getParameters();
		            modelAndView.addObject("reportParameters", paramDtos);
		            return modelAndView;
			}
		}
		return new ModelAndView(new RedirectView("reportList.selfserve", true));
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		return super.formBackingObject(request);
	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		// Convert multipart object to byte[]
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
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
