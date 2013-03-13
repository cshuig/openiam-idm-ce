package org.openiam.idm.srvc.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileService {
	private String pathToCSV;

	/**
	 * @param pathToCSV
	 *            the pathToCSV to set
	 */
	public void setPathToCSV(String pathToCSV) {
		this.pathToCSV = pathToCSV;
	}

	private static final Log log = LogFactory.getLog(FileService.class);

	private String readFile(File file) {
		int ch;
		StringBuffer strContent = new StringBuffer("");
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			while ((ch = fin.read()) != -1)
				strContent.append((char) ch);
			fin.close();
		} catch (Exception e) {
			log.info(e);
		}
		return strContent.toString();
	}

	public String getFile(String fName) throws Exception {
		File file = new File(pathToCSV + fName);
		if (!file.exists()) {
			log.info("FILE: " + pathToCSV + fName + "NOT EXIST");
			return null;
		}
		return readFile(file);
	}

	public File saveFile(String fName, String value) throws Exception {
		FileWriter fw = new FileWriter(pathToCSV + fName, false);
		fw.append(value);
		fw.flush();
		fw.close();

		return new File(pathToCSV + fName);
	}
}
