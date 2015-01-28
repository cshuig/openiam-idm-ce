package org.openiam.ui.selfservice.web.mvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.openiam.idm.srvc.report.dto.ReportSubscriptionDto;
import org.openiam.idm.srvc.report.ws.ReportWebService;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.selfservice.web.model.SubscribeReportBean;
import org.openiam.ui.selfservice.web.model.SubscribeReportSearchResultBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SubscribeReportRestfulController {

	@Resource(name = "reportServiceClient")
	private ReportWebService reportServiceClient;
	
	@Autowired
	private DozerBeanMapper beanMapper;
	
	//@RequestMapping(value="/subscribeReportSearch", method=RequestMethod.GET)
	public @ResponseBody SubscribeReportSearchResultBean subscribeReportSearch(final HttpServletRequest request,
												   final @RequestParam(required=true, value="from") int from,
												   final @RequestParam(required=true, value="size") int size) {
		
		final List<ReportSubscriptionDto> reportsList = reportServiceClient.getSubscribedReports().getReports();
		
		
		//final int count = (reportsList==null) ? 0 :reportsList.size();
		final int count = reportServiceClient.getSubscribedReportCount();
		final List<SubscribeReportBean> subscribeReport = beanMapper.mapToList(reportsList, SubscribeReportBean.class);
		
		return new SubscribeReportSearchResultBean(count, subscribeReport);
	}
}
