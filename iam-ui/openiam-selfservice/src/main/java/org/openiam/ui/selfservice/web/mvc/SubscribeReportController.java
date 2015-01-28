package org.openiam.ui.selfservice.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.report.dto.ReportCriteriaParamDto;
import org.openiam.idm.srvc.report.dto.ReportInfoDto;
import org.openiam.idm.srvc.report.dto.ReportSubscriptionDto;
import org.openiam.idm.srvc.report.ws.GetAllReportsResponse;
import org.openiam.idm.srvc.report.ws.GetReportParametersResponse;
import org.openiam.idm.srvc.report.ws.ReportWebService;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.selfservice.web.model.SubscribeReportRequest;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SubscribeReportController extends AbstractController {

	private static final Log log = LogFactory
			.getLog(SubscribeReportController.class);

	private static String GLOBAL = "GLOBAL";

	@Value("${org.openiam.selfservice.subscribe.report.root}")
	private String subscribeReportRootMenu;

	@Resource(name = "reportServiceClient")
	private ReportWebService reportServiceClient;

	//@RequestMapping("/subscribe")
	public String getSubscribeReport(final HttpServletRequest request,
			final HttpServletResponse response) {


		request.setAttribute("subscribeReportPageURL",
				"editSubscribeReport.html");
		setMenuTree(request, subscribeReportRootMenu);
		return "report/subscribeReportSearch";
	}

	//@RequestMapping(value = "/editSubscribeReport", method = RequestMethod.GET)
	public String editSubscribeReport(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @RequestParam(required = false, value = "id") String id)

	throws IOException {
		List<KeyNameBean> reportNameInfo = new ArrayList<KeyNameBean>();
		ReportSubscriptionDto reportSubscription = null;
		if (StringUtils.isNotBlank(id)) {
			reportSubscription = reportServiceClient
					.getSubscribedReportById(id).getReport();
			request.setAttribute("getParamNameInfo",
					getParamNameInfo(reportSubscription.getReportInfoId()));

			request.setAttribute("reportSubscription", reportSubscription);
			if (reportSubscription == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;

			}
		} else {
			GetAllReportsResponse reportsResponse = reportServiceClient.getReportsByUserId(getRequesterId(request));
			if (reportsResponse.isSuccess() && CollectionUtils.isNotEmpty(reportsResponse.getReports())) {
				for (final ReportInfoDto p : reportsResponse.getReports()) {
					reportNameInfo.add(new KeyNameBean(p.getReportId(), p.getReportName()));
				}
			}

			request.setAttribute("reportNameInfo", reportNameInfo);
		}
		// setMenuTree(request, attributeEditPageRootMenuName);
		return "report/editSubscribeReport";
	}

	//@RequestMapping(value = "/saveSubscribeReport", method = RequestMethod.POST)
	public String saveSubscribeReport(final HttpServletRequest request,
			@RequestBody SubscribeReportRequest subscribereportRequest) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		Response wsResponse = null;
		ReportSubscriptionDto reportSubscription = new ReportSubscriptionDto();
		reportSubscription.setReportId(subscribereportRequest.getReportId());

		reportSubscription
				.setReportName(subscribereportRequest.getReportName());
		if (subscribereportRequest.getReportId() == null) {
			GetAllReportsResponse reportsResponse = reportServiceClient.getReportsByUserId(getRequesterId(request));
			if (reportsResponse.isSuccess() && CollectionUtils.isNotEmpty(reportsResponse.getReports())) {
				for (final ReportInfoDto p : reportsResponse.getReports()) {
					if (p.getReportId().equalsIgnoreCase(
							subscribereportRequest.getReportName())) {
						reportSubscription.setReportName(p.getReportName());
						reportSubscription.setReportInfoId(subscribereportRequest
								.getReportName());
					}
				}
			}
		} else {
			reportSubscription.setReportName(subscribereportRequest
					.getReportName());
			reportSubscription.setReportInfoId(subscribereportRequest
					.getReportInfoId());
		}
		reportSubscription.setDeliveryFormat(subscribereportRequest
				.getDeliveryFormat());
		reportSubscription.setDeliveryMethod(subscribereportRequest
				.getDeliveryMethod());
		reportSubscription.setDeliveryAudience(subscribereportRequest
				.getDeliveryAudience());

		reportSubscription.setStatus(subscribereportRequest.getStatus());

        reportSubscription.setUserId(getRequesterId(request));

		wsResponse = reportServiceClient.createOrUpdateSubscribedReportInfo(
				reportSubscription, null);

		if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setRedirectURL("editSubscribeReport.html?id="
					+ (String) wsResponse.getResponseValue());

			ajaxResponse.setStatus(HttpServletResponse.SC_OK);
			ajaxResponse.setSuccessToken(new SuccessToken(
					SuccessMessage.SUBSCRIBED_REPORT_SAVED));
		} else {
			ajaxResponse
					.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			Errors error = Errors.SAVE_SUBSCRIPTION_REPORT_FAIL;
			if (wsResponse.getErrorCode() != null) {
				
				switch (wsResponse.getErrorCode()) {
				case SUBSCRIBED_NAME_NOT_SET:
					error = Errors.SUBSCRIBED_NAME_NOT_SET;
					break;
				case SUBSCRIBED_DELIVERY_METHOD_NOT_SET:
					error = Errors.SUBSCRIBED_DELIVERY_METHOD_NOT_SET;
					break;
					
				case SUBSCRIBED_DELIVERY_AUDIENCE_NOT_SET:
					error = Errors.SUBSCRIBED_DELIVERY_AUDIENCE_NOT_SET;
					break;
				case SUBSCRIBED_DELIVERY_FORMAT_NOT_SET:
					error = Errors.SUBSCRIBED_DELIVERY_FORMAT_NOT_SET;
					break;
				
				default:
					error = Errors.INTERNAL_ERROR;
					break;
				}
			}

			ajaxResponse.addError(new ErrorToken(error));
		}

		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}

	//@RequestMapping(value = "/deleteSubscribeReport", method = RequestMethod.POST)
	public String deleteSubscribeReport(
			final @RequestParam(value = "reportId") String reportId,
			final HttpServletResponse response, final HttpServletRequest request)
			throws IOException {

		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final Response wsResponse = reportServiceClient
				.deleteSubscribedReport(reportId);

		if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {

			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL("subscribe.html");
			ajaxResponse.setSuccessToken(new SuccessToken(
					SuccessMessage.SUBSCRIBED_REPORT_DELETED));
		} else {
			// TODO check error for foreign key, and mention child exists
			ajaxResponse.addError(new ErrorToken(
					Errors.DELETE_SUBSCRIPTION_REPORT_FAIL));
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}

    //@RequestMapping(value = "/testSubscription", method = RequestMethod.POST)
    public String testSubscription(
            final @RequestParam(value = "reportId") String reportId,
            final HttpServletResponse response, final HttpServletRequest request)
            throws IOException {

        Response wsResponse = reportServiceClient.runSubscription(reportId);
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken(
                    SuccessMessage.SUBSCRIPTION_TEST_START_SUCCESS));
        } else {
            ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Errors error = Errors.SUBSCRIPTION_TEST_START_FAIL;
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                    default:
                        error = Errors.INTERNAL_ERROR;
                        break;
                }
            }

            ajaxResponse.addError(new ErrorToken(error));
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }


	private List<KeyNameBean> getParamNameInfo(String reportId) {
		List<KeyNameBean> result = new ArrayList<KeyNameBean>();
		final GetReportParametersResponse criteriaParam = reportServiceClient
				.getReportParametersByReportId(reportId);
		if (criteriaParam != null
				&& CollectionUtils.isNotEmpty(criteriaParam.getParameters())) {
			for (final ReportCriteriaParamDto p : criteriaParam.getParameters()) {
				result.add(new KeyNameBean(p.getId(), p.getName()));
			}
		}

		return result;
	}

}
