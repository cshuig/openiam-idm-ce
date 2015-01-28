package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openiam.base.ws.ResponseCode;
import org.openiam.idm.srvc.report.dto.ReportParamMetaTypeDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.report.dto.ReportInfoDto;
import org.openiam.idm.srvc.report.dto.ReportParamTypeDto;
import org.openiam.idm.srvc.report.ws.GetReportInfoResponse;
import org.openiam.idm.srvc.report.ws.ReportWebService;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.web.model.ReportRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ReportController extends AbstractController {

	private static final Log log = LogFactory.getLog(ReportController.class);

	@Value("${org.openiam.ui.page.report.root.id}")
	private String reportRootMenuName;

	@Value("${org.openiam.upload.root}")
	private String uploadRoot;

	@Resource(name = "reportServiceClient")
	private ReportWebService reportServiceClient;

	@RequestMapping("/report")
	public String getReports(final HttpServletRequest request,
			final HttpServletResponse response) {
		setMenuTree(request, reportRootMenuName);
		return "report/reportSearch";
	}

    final static String REPORT_DATASOURCE_EXTENSION = ".groovy";
    final static String REPORT_DESIGN_EXTENSION = ".rptdesign";

    @RequestMapping(value = "/editReport", method = RequestMethod.GET)
	public String editReport(final HttpServletRequest request,
			final HttpServletResponse response,
			final @RequestParam(required = false, value = "id") String id)
			throws IOException {

        ReportInfoDto report = new ReportInfoDto();
		if (StringUtils.isNotBlank(id)) {

			GetReportInfoResponse reportInfoResponse = reportServiceClient
					.getReport(id);
			report = reportInfoResponse.getReport();
			if (report == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
		}

        addEditPageAttributes(request, report);
		return "report/edit";
	}

    @RequestMapping(value = "/validateReport", method = RequestMethod.POST)
    public String validateReport(final HttpServletRequest request,
                                 @RequestBody ReportRequest reportRequest) {

        final ReportInfoDto report = convertReportRequestToDto(reportRequest);
        BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Errors error = validateSaveRequest(reportRequest, report, false);

        if (error == null) {
            Response wsResponse = reportServiceClient.validateUpdateReportInfo(report);
            if (ResponseStatus.FAILURE.equals(wsResponse.getStatus())) {
                error = interpretErrorCode(wsResponse.getErrorCode());
            }
        }

        if (error == null) {
            ajaxResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            ajaxResponse.addError(new ErrorToken(error));
            ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

	@RequestMapping(value = "/saveReport", method = RequestMethod.POST)
	public String saveReport(final HttpServletRequest request,
			ReportRequest reportRequest) {

        ReportInfoDto report = new ReportInfoDto();
        Errors error;

        try {

            report = convertReportRequestToDto(reportRequest);
            error = validateSaveRequest(reportRequest, report, true);
            if (error == null) {
                if (reportRequest.getNewDesignFile()) {
                    String uploadPath = saveUploadFile(reportRequest.getReportDesignFile());
                    if (uploadPath != null) {
                        report.setReportUrl(FilenameUtils.getName(uploadPath));
                    } else {
                        error = Errors.INTERNAL_ERROR;
                    }
                }
            }

            if (error == null) {
                if (reportRequest.getNewDataSourceFile()) {
                    String uploadPath = saveUploadFile(reportRequest.getReportDataSourceFile());
                    if (uploadPath != null) {
                        report.setReportDataSource(FilenameUtils.getName(uploadPath));
                    } else {
                        error = Errors.INTERNAL_ERROR;
                    }
                }
            }

			if (error == null) {
                Response wsResponse = reportServiceClient.createOrUpdateReportInfo(report);
				if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
					final String reportId = wsResponse.getResponseValue().toString();
					if (StringUtils.isNotBlank(reportId)) {
						GetReportInfoResponse reportInfoResponse = reportServiceClient
								.getReport(reportId);
						report = reportInfoResponse.getReport();
					}
				} else {
                    error = interpretErrorCode(wsResponse.getErrorCode());
                }
			}

		} catch (Throwable e) {
			error = Errors.INTERNAL_ERROR;
			log.error("Exception while saving report", e);
		}

        addEditPageAttributes(request, report);

        if (error == null) {
            request.setAttribute("successToken", new SuccessToken(SuccessMessage.REPORT_SAVED));
        } else {
            request.setAttribute("errorToken", new ErrorToken(error));
        }
        return "report/edit";
	}

    private ReportInfoDto convertReportRequestToDto(ReportRequest request) {

        ReportInfoDto report = new ReportInfoDto();
        report.setReportId(request.getReportId());
		report.setReportName(request.getReportName());
		report.setResourceId(request.getResourceId());

        boolean newDesignFile = request.getNewDesignFile() && request.getReportDesignFile() != null;
        report.setReportUrl(newDesignFile
                ? request.getReportDesignFile().getOriginalFilename()
                : FilenameUtils.getName(request.getReportDesignName()));

        boolean newDataSourceFile = request.getNewDataSourceFile() && request.getReportDataSourceFile() != null;
        report.setReportDataSource(newDataSourceFile
                ? request.getReportDataSourceFile().getOriginalFilename()
                : FilenameUtils.getName(request.getReportDataSourceName()));

        return report;
    }

    private Errors interpretErrorCode(ResponseCode responseCode) {
        if (responseCode != null) {
            switch (responseCode) {
            case REPORT_NAME_NOT_SET:
                return Errors.INVALID_REPORT_NAME;
            case REPORT_DATASOURCE_NOT_SET:
                return Errors.INVALID_REPORT_DATASOURCE;
            case REPORT_URL_NOT_SET:
                return Errors.INVALID_REPORT_URL;
            case NAME_TAKEN:
                return Errors.REPORT_NAME_TAKEN;
            case READONLY:
                return Errors.BUILT_IN_REPORT_UPDATE_NAME_ERROR;
            default:
                return Errors.SAVE_REPORT_FAIL;
            }
        }
        return null;
    }

    private void addEditPageAttributes(HttpServletRequest request, ReportInfoDto report) {
        request.setAttribute("reportInfoDto", report);
        request.setAttribute("REPORT_DATASOURCE_TAKEN", Errors.REPORT_DATASOURCE_TAKEN.getCode());
        request.setAttribute("REPORT_URL_TAKEN", Errors.REPORT_URL_TAKEN.getCode());
        request.setAttribute("reportDataSources", getFilesList(getReportsFolder(), REPORT_DATASOURCE_EXTENSION));
        request.setAttribute("reportDesigns", getFilesList(getReportsFolder(), REPORT_DESIGN_EXTENSION));
        if (report.getReportId() != null) {
            request.setAttribute("paramTypeNameList", getParamTypes());
            request.setAttribute("paramMetaTypeNameList", getParamMetaTypes());
			if (StringUtils.isNotBlank(report.getResourceId())) {
				org.openiam.idm.srvc.res.dto.Resource res =
					resourceDataService.getResource(report.getResourceId(), getCurrentLanguage());
				request.setAttribute("linkedResource", res);
			}
		}
    }

    private String saveUploadFile(MultipartFile file) {
        try {
            final String uploadPath = genUploadPath(file.getOriginalFilename());
            FileUtils.forceMkdir(new File(FilenameUtils.getFullPath(uploadPath)));
            file.transferTo(new File(uploadPath));
            return uploadPath;
        } catch (IOException e) {
            log.error("Unable to upload file. ", e);
            return null;
        }
    }

    private Errors validateSaveRequest(ReportRequest reportRequest, ReportInfoDto report, boolean validateMultipart) {

        if (StringUtils.isBlank(report.getReportDataSource())) {
            return Errors.INVALID_REPORT_DATASOURCE;
        }

        if (StringUtils.isBlank(report.getReportUrl())) {
            return Errors.INVALID_REPORT_URL;
        }

        if (!reportRequest.getNewDataSourceFile()) {

            if (! new File(genUploadPath(report.getReportDataSource())).exists()) {
                return Errors.REPORT_DATASOURCE_NOT_EXISTS;
            }
        } else {
            if (validateMultipart) {
                if (reportRequest.getReportDataSourceFile() == null
                        || reportRequest.getReportDataSourceFile().isEmpty()) {
                    return Errors.INVALID_REPORT_DATASOURCE;
                }
            }

            final String uploadPath = genUploadPath(report.getReportDataSource());
            if(!reportRequest.getOverwriteDataSourceFile() && new File(uploadPath).exists()) {
                return Errors.REPORT_DATASOURCE_TAKEN;
            }
        }

        if (!reportRequest.getNewDesignFile()) {

            if (! new File(genUploadPath(report.getReportUrl())).exists()) {
                return Errors.REPORT_URL_NOT_EXISTS;
            }
        } else {

            if (validateMultipart) {
                if (reportRequest.getReportDesignFile() == null
                        || reportRequest.getReportDesignFile().isEmpty()) {
                    return Errors.INVALID_REPORT_URL;
                }
            }

            final String uploadPath = genUploadPath(report.getReportUrl());
            if(!reportRequest.getOverwriteDesignFile() && new File(uploadPath).exists()) {
                return Errors.REPORT_URL_TAKEN;
            }
        }
        return null;
    }


	@RequestMapping(value = "/deleteReport", method = RequestMethod.POST)
	public String deleteReport(final @RequestParam(value = "id") String id,
			final HttpServletResponse response, final HttpServletRequest request)
			throws IOException {

		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		// TODO delete associated data source and design file also
		final Response wsResponse = reportServiceClient.deleteReport(id);

		if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {

			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL("report.html");
			ajaxResponse.setSuccessToken(new SuccessToken(
					SuccessMessage.REPORT_DELETED));
		} else {
            Errors error = null;
            switch (wsResponse.getErrorCode()) {
                case OBJECT_NOT_FOUND:
                    error = Errors.OBJECT_DOES_NOT_EXIST;
                    break;
                case PERMISSION_EXCEPTION:
                    error = Errors.BUILT_IN_REPORT_DELETE_ERROR;
                    break;
                default:
                    error = Errors.DELETE_REPORT_FAIL;
            }
			ajaxResponse.addError(new ErrorToken(error));
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}

	private String genUploadPath(String fileName) {
        return getReportsFolder() + FilenameUtils.getName(fileName);
	}

    private String getReportsFolder() {
        return uploadRoot + "/report/";
    }

    private List<KeyNameBean> getParamTypes() {
		List<KeyNameBean> result = new ArrayList<KeyNameBean>();
		final List<ReportParamTypeDto> paramTypes = reportServiceClient
				.getReportParameterTypes().getTypes();
		if (CollectionUtils.isNotEmpty(paramTypes)) {
			for (final ReportParamTypeDto p : paramTypes) {
				result.add(new KeyNameBean(p.getId(), p.getName()));

			}

		}
		return result;
	}

    private List<KeyNameBean> getParamMetaTypes() {
        List<KeyNameBean> result = new ArrayList<KeyNameBean>();
        final List<ReportParamMetaTypeDto> paramMetaTypes = reportServiceClient
                .getReportParameterMetaTypes().getMetaTypes();
        if (CollectionUtils.isNotEmpty(paramMetaTypes)) {
            for (final ReportParamMetaTypeDto p : paramMetaTypes) {
                result.add(new KeyNameBean(p.getId(), p.getName()));
            }
        }
        return result;
    }

    private List<KeyNameBean> getFilesList(final String path, final String extension) {
        List<KeyNameBean> beans = new ArrayList<>();
        File folder = new File(path);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return (file.isFile() && file.getName().endsWith(extension));
                }
            });

            for(File file : files) {
                final String fileName = file.getName();
                beans.add(new KeyNameBean(fileName, FilenameUtils.getBaseName(fileName)));
            }

            Collections.sort(beans, new Comparator<KeyNameBean>() {
                @Override
                public int compare(KeyNameBean o1, KeyNameBean o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
        }
        return beans;
    }
}
