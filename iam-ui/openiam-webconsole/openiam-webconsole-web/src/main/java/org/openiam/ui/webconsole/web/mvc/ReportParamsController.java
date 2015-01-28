package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.report.dto.ReportCriteriaParamDto;
import org.openiam.idm.srvc.report.ws.ReportWebService;

import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.web.model.ReportParamBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ReportParamsController extends AbstractController {
	private static Logger log = Logger.getLogger(ReportParamsController.class);
	@Resource(name = "reportServiceClient")
	private ReportWebService reportServiceClient;

	@RequestMapping(value = "/getParamsForReport", method = RequestMethod.GET)
	public @ResponseBody
	BeanResponse getParamsForReport(final HttpServletRequest request,
			final @RequestParam(required = true, value = "reportId") String reportId,
			final @RequestParam(required = true, value = "size") Integer size,
			final @RequestParam(required = true, value = "from") Integer from) {
		
		final List<ReportCriteriaParamDto> paramsList = reportServiceClient
				.getReportParametersByReportId(reportId).getParameters();

		final Integer count = (paramsList == null) ? 0 : paramsList.size();// reportServiceClient.getNumOfServersForProvider(providerId);
		final List<ReportParamBean> beanList = new LinkedList<ReportParamBean>();
		
		if (CollectionUtils.isNotEmpty(paramsList)) {
    			for (final ReportCriteriaParamDto param : paramsList) {
				beanList.add(new ReportParamBean(param));
			}
		}
		Collections.sort(beanList);
		return new BeanResponse(beanList, count);
	}

	@RequestMapping(value = "/saveReportParam", method = RequestMethod.POST)
	public String saveReportParam(final HttpServletRequest request,
			@RequestBody ReportParamBean reportParameter) {
		
		
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		try {
			ReportCriteriaParamDto reportParam = convertToDTO(reportParameter);
		
			
			Response wsResponse = reportServiceClient
					.createOrUpdateReportInfoParam(reportParam);

			if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
				SuccessToken successToken = new SuccessToken(
						SuccessMessage.REPORT_PARAMETER_SAVED);
				ajaxResponse.setStatus(200);
				ajaxResponse.setSuccessToken(successToken);
			} else {
				Errors error = Errors.SAVE_REPORT_FAIL;
				if (wsResponse.getErrorCode() != null) {
					switch (wsResponse.getErrorCode()) {
					case REPORT_PARAM_NAME_NOT_SET:
						error = Errors.REPORT_PARAMETER_NAME_NOT_SET;
						break;
					case REPORT_PARAM_TYPE_NOT_SET:
						error = Errors.REPORT_PARAMETER_TYPE_NOT_SET;
						break;
					
					case REPORT_PARAMETER_EXISTS:
						error = Errors.REPORT_PARAMETER_EXISTS;
						break;
					case NAME_TAKEN:
						error = Errors.REPORT_PARAMETER_NAME_TAKEN;
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

	@RequestMapping(value = "/deleteReportParam", method = RequestMethod.POST)
	public String deleteReportParam(final HttpServletRequest request,
			@RequestBody ReportParamBean reportParam) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		try {

			Response wsResponse = reportServiceClient
					.deleteReportParam(reportParam.getId());

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

	private ReportCriteriaParamDto convertToDTO(ReportParamBean paramRequest) {
		ReportCriteriaParamDto dto = new ReportCriteriaParamDto();
		dto.setId(paramRequest.getId());
        dto.setCaption(paramRequest.getParamCaption());
        dto.setName(paramRequest.getParamName());
	    dto.setTypeId(paramRequest.getParamTypeId());
		dto.setReportId(paramRequest.getReportId());
        dto.setMetaTypeId(paramRequest.getParamMetaTypeId());
        dto.setIsMultiple(paramRequest.getIsMultiple());
        dto.setIsRequired(paramRequest.getIsRequired());
		dto.setDisplayOrder(paramRequest.getDisplayOrder());
		dto.setRequestParameters(paramRequest.getRequestParameters());
		return dto;
	}

	}
