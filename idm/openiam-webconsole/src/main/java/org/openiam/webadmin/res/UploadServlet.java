package org.openiam.webadmin.res;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.util.TempFile;
import org.apache.xmlbeans.impl.common.ReaderInputStream;
import org.springframework.util.StringUtils;

import com.lowagie.text.pdf.codec.Base64.OutputStream;

public class UploadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TMP_DIR_PATH = "/tmp";
	private File tmpDir;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		tmpDir = new File(TMP_DIR_PATH);
		if (!tmpDir.isDirectory()) {
			throw new ServletException(TMP_DIR_PATH + " is not a directory");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
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

		String DESTINATION_DIR_PATH = "";
		String FILE_NAME = "";
		String resId = "";
		char seprator = 0;
		char endOfLine = 0;
		File destinationDir;

		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		try {
			/*
			 * Parse the request
			 */
			FileItem resultItem = null;
			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				/*
				 * Handle Form Fields.
				 */
				if (!item.isFormField()) {
					resultItem = item;
				} else {
					if ("recName".equals(item.getFieldName())) {
						FILE_NAME = item.getString();
					}
					if ("csvDir".equals(item.getFieldName())) {
						DESTINATION_DIR_PATH = item.getString();
					}
					if ("csvSep".equals(item.getFieldName())) {
						seprator = getCharFromString(item.getString());
					}
					if ("csvEOL".equals(item.getFieldName())) {
						endOfLine = getCharFromString(item.getString());
					}
					if ("rId".equals(item.getFieldName())) {
						resId = item.getString();
					}
				}
			}
			if (!StringUtils.hasText(DESTINATION_DIR_PATH)) {
				throw new ServletException("DESTINATION_DIR_PATH is empty");
			}

			if (!StringUtils.hasText(FILE_NAME)) {
				throw new ServletException("FILE_NAME is empty");
			}

			destinationDir = new File(DESTINATION_DIR_PATH);
			if (!destinationDir.isDirectory()) {
				throw new ServletException(DESTINATION_DIR_PATH
						+ " is not a directory");
			}

			File file = new File(FILE_NAME);
			resultItem.write(file);
			log("File successfuly uploaded to: " + DESTINATION_DIR_PATH
					+ FILE_NAME);
//			// Update CSV according to internal format: 'a,b,c,d\n'
//			if (',' == seprator && '\n' == endOfLine) {
//				log("Format is as internal");
//			} else {
//				Calendar calendar = Calendar.getInstance();
//				log("CSV Remake start in ");
//				this.remakeFile(file, seprator, endOfLine);
//				log("CSV Remake complete after"
//						+ (Calendar.getInstance().getTimeInMillis() - calendar
//								.getTimeInMillis()) / 1000 + "sec");
//			}
			response.sendRedirect(request.getContextPath()
					+ "/reconcilConfig.cnt?menuid=RESRECONCILE&menugrp=SECURITY_RES&objId="
					+ resId);
		} catch (FileUploadException ex) {
			log("Error encountered while parsing the request", ex);
		} catch (Exception ex) {
			log("Error encountered while uploading file", ex);
		}

	}

	private char getCharFromString(String str) {
		if (!StringUtils.hasText(str) || "comma".equals(str))
			return ',';
		if ("tab".equals(str))
			return '\t';
		if ("semicolon".equals(str))
			return ';';
		if ("space".equals(str))
			return ' ';
		if ("enter".equals(str))
			return '\n';
		return ',';
	}

	private void remakeFile(File file, char sep, char eol) throws IOException {
		int ch;
		StringBuffer strContent = new StringBuffer("");
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			while ((ch = fin.read()) != -1)
				strContent.append((char) ch);
			fin.close();
		} catch (Exception e) {
			log(e.getMessage());
		}
		String result = "";
		if (',' != sep) {
			result = strContent.toString().replace(',', ' ').replace(sep, ',')
					.replace(eol, '\n');
		} else {
			result = strContent.toString().replace(eol, '\n');
		}
		FileWriter fstream = new FileWriter(file);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(result);
		// Close the output stream
		out.close();
	}
}
