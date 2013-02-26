package org.openiam.spml2.spi.csv;

import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.spml2.msg.ErrorCode;
import org.openiam.spml2.msg.LookupRequestType;
import org.openiam.spml2.msg.LookupResponseType;
import org.openiam.spml2.msg.StatusCodeType;

public class LookupCSVCommand extends AbstractCSVCommand {
	public LookupResponseType lookup(LookupRequestType reqType) {
		LookupResponseType response = new LookupResponseType();
		response.setStatus(StatusCodeType.SUCCESS);
		log.debug("add request called..");

		String principal = reqType.getPsoID().getID();
		/*
		 * A) Use the targetID to look up the connection information under
		 * managed systems
		 */
		ManagedSys managedSys = managedSysService.getManagedSys(reqType
				.getPsoID().getTargetID());

		// Initialise
		try {
			ReconciliationConfig conf = reconcileService.getConfigByResource(
					managedSys.getResourceId()).getConfig();
			if (this.lookupObjectInCSV(principal, managedSys,
					response.getAny(), this.getSeparator(conf),
					this.getEndOfLine(conf))) {
				response.setStatus(StatusCodeType.SUCCESS);

			} else
				response.setStatus(StatusCodeType.FAILURE);
		} catch (Exception e) {
			e.printStackTrace();

			log.error(e);
			// return a response object - even if it fails so that it can be
			// logged.
			response.setStatus(StatusCodeType.FAILURE);
			response.setError(ErrorCode.CSV_ERROR);
			response.addErrorMessage(e.toString());

		}
		return response;
	}
}
