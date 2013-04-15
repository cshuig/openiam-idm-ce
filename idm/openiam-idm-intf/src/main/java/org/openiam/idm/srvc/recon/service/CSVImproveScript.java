package org.openiam.idm.srvc.recon.service;

import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.provision.dto.ProvisionUser;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: Pascal Date: 30.04.12 Time: 19:59 To change
 * this template use File | Settings | File Templates.
 */
public interface CSVImproveScript {
	public int execute(String pathToFile, List<Organization> orgList);
}
