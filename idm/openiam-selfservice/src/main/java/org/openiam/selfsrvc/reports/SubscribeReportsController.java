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
import org.openiam.idm.srvc.report.ws.GetReportParametersResponse;
import org.openiam.idm.srvc.report.ws.WebReportService;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class SubscribeReportsController extends CancellableFormController {
	private static ResourceBundle res = ResourceBundle
			.getBundle("securityconf");

	private WebReportService reportService;
	private String reportName = "";

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		HttpSession session = request.getSession(true);
	 	session.removeAttribute("reportCommand");
		SubscribeReportsCommand reportCommand = ((SubscribeReportsCommand) command);
		if (request.getParameterMap().containsKey("save")) {
			if (!StringUtils.isEmpty(reportCommand.getReport().getReportName())) {
				List<ReportSubCriteriaParamDto> params = new LinkedList<ReportSubCriteriaParamDto>();
				if (reportCommand.getParamName() != null) {
					for (int i = 0; i < reportCommand.getParamName().length; i++) {
						//String paramTypeId = reportCommand.getParamTypeId()[i];
						params.add(new ReportSubCriteriaParamDto(reportCommand
								.getReport().getReportId(), reportCommand
								.getParamName()[i], reportCommand
								.getParamValue()[i]));
					}
				}
		        //HttpSession session = request.getSession();
		        String userId = (String) session.getAttribute("userId");
				ReportSubscriptionDto dto = reportCommand.getReport();
				dto.setUserId(userId);
				if (dto.getReportId() == null || "".equals(dto.getReportId()) || dto.getReportId().trim().length() <=0)
					dto.setReportId(null);
				reportService.createOrUpdateSubscribedReportInfo(
						reportCommand.getReport(), params);
				return new ModelAndView(new RedirectView("subscribeReport.selfserve", true));
			}
		}else{
			reportName = reportCommand.getReport().getReportName();
			if (!StringUtils.isEmpty(reportCommand.getReport().getReportName())) {
				 ModelAndView modelAndView = new ModelAndView(new RedirectView("subscribeReportOld.selfserve", true));
		            List<ReportCriteriaParamDto> paramDtos = reportService.getReportParametersByReportName(reportCommand.getReport().getReportName()).getParameters();
		            modelAndView.addObject("reportParameters", paramDtos);
		            modelAndView.addObject("reportCommand", reportCommand);
				    //HttpSession session = request.getSession(true);
				 	session.setAttribute("reportCommand", reportCommand);
		            return modelAndView;
			}
		}
		return new ModelAndView(new RedirectView("subscribeReportOld.selfserve", true));
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
				
		java.util.List<org.openiam.idm.srvc.report.dto.ReportSubCriteriaParamDto> reportParameters = new java.util.ArrayList<org.openiam.idm.srvc.report.dto.ReportSubCriteriaParamDto>();
		//setting parameters for the first report only, need to handle in onchange
		boolean paramsSet = false;
		for (ReportInfoDto reportInfoDto : reportsList) {
			reports.put(reportInfoDto.getReportName(),
					reportInfoDto.getReportName());
			if (!paramsSet){
				if (null == reportName || reportName.trim().length() <= 0){
					//use the first report as report Name else use the selected value
					reportName = reportInfoDto.getReportName();
				}
				GetReportParametersResponse paramResponse = reportService.getReportParametersByReportName(reportName);
				List<ReportCriteriaParamDto> params = paramResponse.getParameters();
				if (null != params){
					for (ReportCriteriaParamDto param: params){
						ReportSubCriteriaParamDto subParamDto = new ReportSubCriteriaParamDto();
						subParamDto.setReportId(reportInfoDto.getReportId());
						subParamDto.setName(param.getName());
						subParamDto.setTypeId(param.getTypeId());
						reportParameters.add(subParamDto);
					}
				}
				referenceData.put("reportParameters", reportParameters);
				paramsSet = true;
			}
		}
		referenceData.put("reportsList", reports);
		
		return referenceData;
	}
}
