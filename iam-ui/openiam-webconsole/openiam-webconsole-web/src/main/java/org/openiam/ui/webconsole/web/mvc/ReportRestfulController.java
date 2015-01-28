package org.openiam.ui.webconsole.web.mvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.openiam.idm.srvc.report.dto.ReportInfoDto;
import org.openiam.idm.srvc.report.ws.ReportWebService;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.webconsole.web.model.ReportBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportRestfulController {

	@Resource(name = "reportServiceClient")
	private ReportWebService reportServiceClient;
	
	@Autowired
	private DozerBeanMapper beanMapper;

	@Autowired
	protected OpenIAMCookieProvider cookieProvider;

	@RequestMapping(value="/searchReports", method=RequestMethod.GET)
	public @ResponseBody BeanResponse searchReports(final HttpServletRequest request,
												   final @RequestParam(required=true, value="from") int from,
												   final @RequestParam(required=true, value="size") int size) {
		final List<ReportInfoDto> reportsList = reportServiceClient.getReports(from, size).getReports();
		final int count = reportServiceClient.getReportCount();
		final List<ReportBean> reportBeanList = beanMapper.mapToList(reportsList, ReportBean.class);
		return new BeanResponse(reportBeanList, count);
	}


}
