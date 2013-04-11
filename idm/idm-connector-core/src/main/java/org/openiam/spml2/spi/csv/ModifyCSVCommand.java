package org.openiam.spml2.spi.csv;

import java.util.List;

import org.openiam.idm.srvc.csv.CSVParser;
import org.openiam.idm.srvc.csv.ReconciliationObject;
import org.openiam.idm.srvc.csv.constant.CSVSource;
import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.spml2.msg.DeleteRequestType;
import org.openiam.spml2.msg.ErrorCode;
import org.openiam.spml2.msg.ModifyRequestType;
import org.openiam.spml2.msg.ModifyResponseType;
import org.openiam.spml2.msg.PSOIdentifierType;
import org.openiam.spml2.msg.ResponseType;
import org.openiam.spml2.msg.StatusCodeType;

public class ModifyCSVCommand extends AbstractCSVCommand {
	private CSVParser<ProvisionUser> provisionUserCSVParser;

	public ModifyResponseType modify(ModifyRequestType reqType) {
		ModifyResponseType response = new ModifyResponseType();
		response.setStatus(StatusCodeType.SUCCESS);
		log.debug("modify request called..");

		PSOIdentifierType psoID = reqType.getPsoID();
		/* targetID - */
		String targetID = psoID.getTargetID();

		// Data sent with request - Data must be present in the request per the
		// spec
		ManagedSys managedSys = managedSysService.getManagedSys(targetID);

		// Initialise
		try {
			ProvisionUser user = reqType.getpUser();
			if (user == null) {
				response.setStatus(StatusCodeType.FAILURE);
				response.setError(ErrorCode.CSV_ERROR);
				response.addErrorMessage("Sync object is null");
			}
			this.updatePUser(
					new ReconciliationObject<ProvisionUser>(psoID.getID(), user),
					managedSys);
		} catch (Exception e) {
			e.printStackTrace();

			log.error(e);
			response.setStatus(StatusCodeType.FAILURE);
			response.setError(ErrorCode.CSV_ERROR);
			response.addErrorMessage(e.toString());

		}
		return response;
	}

	public ResponseType delete(DeleteRequestType reqType) {
		ResponseType response = new ResponseType();
		response.setStatus(StatusCodeType.SUCCESS);
		log.debug("modify request called..");

		PSOIdentifierType psoID = reqType.getPsoID();
		/* targetID - */
		String targetID = psoID.getTargetID();

		// Data sent with request - Data must be present in the request per the
		// spec
		ManagedSys managedSys = managedSysService.getManagedSys(targetID);

		// Initialise
		try {
			ProvisionUser user = reqType.getpUser();
			if (user == null) {
				response.setStatus(StatusCodeType.FAILURE);
				response.setError(ErrorCode.CSV_ERROR);
				response.addErrorMessage("Sync object is null");
			}
			this.deleteUser(psoID.getID(), user, managedSys);
		} catch (Exception e) {
			e.printStackTrace();

			log.error(e);
			response.setStatus(StatusCodeType.FAILURE);
			response.setError(ErrorCode.CSV_ERROR);
			response.addErrorMessage(e.toString());

		}
		return response;
	}

	protected void updatePUser(ReconciliationObject<ProvisionUser> newUser,
			ManagedSys managedSys) throws Exception {
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		provisionUserCSVParser.update(newUser, managedSys, attrMapList,
				CSVSource.IDM);
	}

	/**
	 * @param provisionUserCSVParser the provisionUserCSVParser to set
	 */
	public void setProvisionUserCSVParser(
			CSVParser<ProvisionUser> provisionUserCSVParser) {
		this.provisionUserCSVParser = provisionUserCSVParser;
	}
}
