package org.openiam.idm.srvc.recon.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.recon.service.ReconciliationCommand;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.service.ProvisionService;
import org.openiam.provision.type.ExtensibleAttribute;

import java.util.List;

public class CreateResourceAccountCommand implements ReconciliationCommand {
    private ProvisionService provisionService;
    private static final Log log = LogFactory.getLog(CreateResourceAccountCommand.class);

    public CreateResourceAccountCommand(ProvisionService provisionService) {
        this.provisionService = provisionService;
    }

    public boolean execute(Login login, ProvisionUser user, List<ExtensibleAttribute> attributes) {
        log.debug("Entering CreateResourceAccountCommand");
        log.debug("Create Resource Account for user: " + user.getUserId());
        ProvisionUser pUser = user != null ? user : new ProvisionUser();
        provisionService.modifyUser(pUser);
        return true;
    }
}
