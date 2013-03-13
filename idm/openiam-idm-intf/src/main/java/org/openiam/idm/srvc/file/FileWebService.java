package org.openiam.idm.srvc.file;

import java.io.File;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Interface for <code>ReconciliationWebService</code>. Service is responsible
 * for activities related to reconcilation persisted through this service.
 */
@WebService(targetNamespace = "http://www.openiam.org/service/file", name = "FileWebService")
public interface FileWebService {

	@WebMethod
	String getFile(
			@WebParam(name = "fileName", targetNamespace = "") String fName);

	@WebMethod
	File saveFile(
			@WebParam(name = "fileName", targetNamespace = "") String fName,
			@WebParam(name = "fileContent", targetNamespace = "") String value);

}