package org.openiam.ui.selfservice.web.mvc;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.selfservice.web.model.SubCriteriaParamReportBean;
import org.openiam.ui.selfservice.web.model.SubCriteriaParamReportSearchResultBean;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewReportRestfulController extends AbstractController {

	@Autowired
	private DozerBeanMapper beanMapper;

	@Value("${org.openiam.generated.reports.location}")
	private String reportRoot;

    @Value("${org.openiam.upload.remote.use}")
    private Boolean useRemoteFilestorage;

    final private String[] REPORT_FILE_EXTENSIONS = new String[] {".pdf", ".xls", ".html", ".htm"};

	//@RequestMapping(value = "/viewReportSearch", method = RequestMethod.GET)
	public @ResponseBody
	SubCriteriaParamReportSearchResultBean viewReportSearch(
			final HttpServletRequest request,
			final @RequestParam(required = true, value = "from") int from,
			final @RequestParam(required = true, value = "size") int size) {

        List<SubCriteriaParamReportBean> reportBeans = new ArrayList<SubCriteriaParamReportBean>();

        // TODO: implement remote folder listing (useRemoteFilestorage)

        final String userId = getRequesterId(request);
        final File reportFolder = new File(reportRoot + File.separator + userId);
        final File[] reportFiles = reportFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                for (String extension : REPORT_FILE_EXTENSIONS) {
                    if (file.isFile()
                            && file.getName().toLowerCase().endsWith(extension)) {
                        return true;
                    }
                }
                return false;
            }
        });

        if (reportFiles != null) {
            for (File file : reportFiles) {
                final String fileName = file.getName();
                SubCriteriaParamReportBean bean = new SubCriteriaParamReportBean();
                //bean.setName(fileName);
                bean.setId(fileName);
                reportBeans.add(bean);
            }
        }

        final int count = reportBeans.size();
		final List<SubCriteriaParamReportBean> viewReport = beanMapper
				.mapToList(reportBeans, SubCriteriaParamReportBean.class);
		return new SubCriteriaParamReportSearchResultBean(count, viewReport);
	}

}
