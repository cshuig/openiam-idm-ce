package org.openiam.ui.selfservice.web.mvc;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.report.ws.ReportWebService;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;

@Controller
public class ViewReportController extends AbstractController {

	private static final Log log = LogFactory
			.getLog(ViewReportController.class);

	@Value("${org.openiam.selfservice.view.report.root}")
	private String viewReportRootMenu;

	@Resource(name = "reportServiceClient")
	private ReportWebService reportServiceClient;

    @Value("${org.openiam.generated.reports.location}")
	private String reportRoot;

	
	//@RequestMapping("/view")
	public String getViewReport(final HttpServletRequest request,
			final HttpServletResponse response) {
		request.setAttribute("viewReportPageURL", "editViewReport.html");
		return "report/viewReportSearch";
	}

	//@RequestMapping(value = "/editViewReport", method = RequestMethod.GET)
	public ModelAndView editViewReport(final HttpServletRequest request,
			final HttpServletResponse response,
			final @RequestParam(required = false, value = "id") String reportName)
			throws IOException {
		
        if (StringUtils.isNotEmpty(reportName)) {
            final String userId = getRequesterId(request);
            final String reportPath = reportRoot + File.separator + userId + File.separator + reportName;
            final File reportFile = new File(reportPath);
            if (reportFile.exists()) {
                response.setHeader( "Content-Disposition", "attachment;filename=" + reportName );
                final String contentType = getContentType(reportFile);
                response.setContentType(contentType);
                response.setContentLength((int) reportFile.length());
                OutputStream os = response.getOutputStream();
                InputStream is = new FileInputStream(reportFile);
                IOUtils.copy(is, os);
                os.flush();
                os.close();
            }
	}
        return null;
	}

    private String getContentType(File file) {
        try {
            return Files.probeContentType(file.toPath());
        } catch (IOException e) {
            log.info(String.format("Can't get the file '%s' content type", file.getName()), e);
            return "application/octet-stream";
        }
    }
}
