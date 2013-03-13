/*
 * Copyright 2009, OpenIAM LLC 
 * This file is part of the OpenIAM Identity and Access Management Suite
 *
 *   OpenIAM Identity and Access Management Suite is free software: 
 *   you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License 
 *   version 3 as published by the Free Software Foundation.
 *
 *   OpenIAM is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   Lesser GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenIAM.  If not, see <http://www.gnu.org/licenses/>. *
 */

/**
 * 
 */
package org.openiam.idm.srvc.file;

import java.io.File;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author suneet
 * 
 */
@WebService(endpointInterface = "org.openiam.idm.srvc.file.FileWebService", targetNamespace = "http://www.openiam.org/service/file", portName = "FileWebServicePort", serviceName = "FileWebService")
public class FileWebServiceImpl implements FileWebService {

	protected FileService fileService;

	@Override
	@WebMethod
	public String getFile(
			@WebParam(name = "fileName", targetNamespace = "") String fName) {
		try {
			return fileService.getFile(fName);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * @param fileService
	 *            the fileService to set
	 */
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@Override
	@WebMethod
	public File saveFile(
			@WebParam(name = "fileName", targetNamespace = "") String fName,
			@WebParam(name = "fileContent", targetNamespace = "") String value) {
		try {
			return fileService.saveFile(fName, value);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

}
