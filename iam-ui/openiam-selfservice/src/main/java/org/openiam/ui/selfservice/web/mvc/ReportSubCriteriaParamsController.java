package org.openiam.ui.selfservice.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.report.dto.ReportSubCriteriaParamDto;
import org.openiam.idm.srvc.report.ws.ReportWebService;

import org.openiam.ui.selfservice.web.model.SubCriteriaParamReportBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
public class ReportSubCriteriaParamsController extends AbstractController {
	private static Logger log = Logger.getLogger(ReportSubCriteriaParamsController.class);
	@Resource(name = "reportServiceClient")
	private ReportWebService reportServiceClient;

	@RequestMapping(value = "/getSubCriteriaParamsReport", method = RequestMethod.GET)
	public @ResponseBody
	BeanResponse getSubCriteriaParamsReport(
			final @RequestParam(required = true, value = "reportId") String reportId,
			final @RequestParam(required = true, value = "size") Integer size,
			final @RequestParam(required = true, value = "from") Integer from) {
	
		final List<ReportSubCriteriaParamDto> paramsList = reportServiceClient.getAllSubCriteriaParamReport(reportId).getReports();
				
		final Integer count = (paramsList == null) ? 0 : paramsList.size();
		final List<SubCriteriaParamReportBean> beanList = new LinkedList<SubCriteriaParamReportBean>();
		
		if (CollectionUtils.isNotEmpty(paramsList)) {
			for (final ReportSubCriteriaParamDto param : paramsList) {
				beanList.add(new SubCriteriaParamReportBean(param.getRscpId(),param.getId(),
						param.getReportId(),param.getName(),param.getValue(),param.getType()));
				
			}
		}

		
		return new BeanResponse(beanList, count);
	}

	@RequestMapping(value = "/saveSubCriteriaParamReport", method = RequestMethod.POST)
	public String saveSubCriteriaParamReport(final HttpServletRequest request,
			@RequestBody SubCriteriaParamReportBean reportParameter) {
			
		
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		try {
			ReportSubCriteriaParamDto subCriteriaParamReport = convertToDTO(reportParameter);
			
					
			Response wsResponse = reportServiceClient
					.createOrUpdateSubCriteriaParam(subCriteriaParamReport);

			if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
				SuccessToken successToken = new SuccessToken(
						SuccessMessage.REPORT_PARAMETER_SAVED);
				ajaxResponse.setStatus(200);
				ajaxResponse.setSuccessToken(successToken);
			} else {
				Errors error = Errors.SAVE_REPORT_FAIL;
				if (wsResponse.getErrorCode() != null) {
					switch (wsResponse.getErrorCode()) {
					case SUBSCRIBED_ID_NOT_SET:
						error = Errors.SUBSCRIBED_ID_NOT_SET;
						break;
					case SUBSCRIBED_VALUE_NOT_SET:
						error = Errors.SUBSCRIBED_VALUE_NOT_SET;
						break;
					
					default:
						error = Errors.INTERNAL_ERROR;
						break;
					}
				}
				ErrorToken errorToken = new ErrorToken(error);
				ajaxResponse.addError(errorToken);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}

	@RequestMapping(value = "/deleteSubCriteriaParamReport", method = RequestMethod.POST)
	public String deleteSubCriteriaParamReport(final HttpServletRequest request,
			@RequestBody SubCriteriaParamReportBean subCriteriaParamReportBean) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		try {
			Response wsResponse = reportServiceClient.deleteSubCriteriaParamReport(subCriteriaParamReportBean.getRscpId());
			if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
				SuccessToken successToken = new SuccessToken(
						SuccessMessage.REPORT_PARAMETER_DELETED);
				ajaxResponse.setStatus(200);
				ajaxResponse.setSuccessToken(successToken);
			} else {
				Errors error = Errors.DELETE_REPORT_PARAM_FAIL;
				if (wsResponse.getErrorCode() != null) {
					switch (wsResponse.getErrorCode()) {
					case INVALID_ARGUMENTS:
						error = Errors.REPORT_PARAMETER_NOT_SELECTED;
						break;
					default:
						error = Errors.INTERNAL_ERROR;
						break;
					}
				}
				ErrorToken errorToken = new ErrorToken(error);
				ajaxResponse.addError(errorToken);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	

	private ReportSubCriteriaParamDto convertToDTO( SubCriteriaParamReportBean paramRequest) {
		ReportSubCriteriaParamDto dto = new ReportSubCriteriaParamDto();
		dto.setRscpId(paramRequest.getRscpId());
        dto.setId(paramRequest.getId());
        dto.setName(paramRequest.getName());
		dto.setReportId(paramRequest.getReportId());
		dto.setValue(paramRequest.getValue());
		dto.setType(paramRequest.getType());
		return dto;
	}

	//
}
