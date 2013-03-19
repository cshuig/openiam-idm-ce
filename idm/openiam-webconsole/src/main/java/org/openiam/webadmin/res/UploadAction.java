package org.openiam.webadmin.res;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openiam.idm.srvc.file.FileWebService;
import org.openiam.idm.srvc.recon.ws.ReconciliationWebService;
import org.springframework.util.StringUtils;
import org.springframework.web.struts.DispatchActionSupport;

public class UploadAction extends DispatchActionSupport {

	protected FileWebService fileWebService;

	@SuppressWarnings("rawtypes")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String TMP_DIR_PATH = "/tmp";
		File tmpDir = new File(TMP_DIR_PATH);
		if (!tmpDir.isDirectory()) {
			throw new ServletException(TMP_DIR_PATH + " is not a directory");
		}
		response.setContentType("text/plain");
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		/*
		 * Set the size threshold, above which content will be stored on disk.
		 */
		fileItemFactory.setSizeThreshold(1 * 1024 * 1024); // 1 MB
		/*
		 * Set the temporary directory to store the uploaded files of size above
		 * threshold.
		 */
		fileItemFactory.setRepository(tmpDir);
		char seprator = 0;
		char endOfLine = 0;
		String resId = (String) request.getSession().getAttribute("resId");
		String mSysId = (String) request.getSession().getAttribute("mSysId");
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		try {
			FileItem resultItem = null;
			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField()) {
					resultItem = item;
				}
				if ("csvSep".equals(item.getFieldName())) {
					seprator = ReconConfigurationController
							.getCharFromString(item.getString());
				}
				if ("csvEOL".equals(item.getFieldName())) {
					endOfLine = ReconConfigurationController
							.getCharFromString(item.getString());
				}
			}

			String fromFile = resultItem.getString();
			if (',' == seprator && '\n' == endOfLine) {
				log.info("Format is as internal");
			} else {
				Calendar calendar = Calendar.getInstance();
				log.debug("CSV Remake start in ");
				this.remakeFile(fromFile, seprator, endOfLine);
				log.debug("CSV Remake complete after"
						+ (Calendar.getInstance().getTimeInMillis() - calendar
								.getTimeInMillis()) / 1000 + "sec");
			}
			fileWebService.saveFile("recon_" + resId + ".csv",
					fromFile);
		} catch (FileUploadException ex) {
			log.error("Error encountered while parsing the request", ex);
		} catch (Exception ex) {
			log.error("Error encountered while uploading file", ex);
		}
		return (new ActionForward(
				"/reconcilConfig.cnt?menuid=RESRECONCILE&menugrp=SECURITY_RES&objId="
						+ resId));
	}

	private void remakeFile(String fromFile, char sep, char eol)
			throws IOException {
		String result = "";
		if (',' != sep) {
			fromFile = fromFile.replace(',', ' ').replace(sep, ',')
					.replace(eol, '\n');
		} else {
			fromFile = fromFile.replace(eol, '\n');
		}
	}

	/**
	 * @param fileWebService
	 *            the fileWebService to set
	 */
	public void setFileWebService(FileWebService fileWebService) {
		this.fileWebService = fileWebService;
	}
}