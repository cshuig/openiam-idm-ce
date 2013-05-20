package org.openiam.idm.srvc.recon.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;
import org.openiam.idm.srvc.recon.service.PopulationScript;
import org.openiam.idm.srvc.recon.service.ReconciliationCommand;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.service.ProvisionService;
import org.openiam.provision.type.ExtensibleAttribute;
import org.openiam.script.ScriptFactory;
import org.openiam.script.ScriptIntegration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Pascal
 * Date: 27.04.12
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class CreateIdmAccountCommand implements ReconciliationCommand {
    private ProvisionService provisionService;
    private ReconciliationSituation config;
    private static final Log log = LogFactory.getLog(CreateIdmAccountCommand.class);
    private static String scriptEngine = "org.openiam.script.GroovyScriptEngineIntegration";
    private PopulationScript script;

    public CreateIdmAccountCommand(ProvisionService provisionService, ReconciliationSituation config) {
        this.provisionService = provisionService;
        this.config = config;
        try {
            ScriptIntegration se = ScriptFactory.createModule(scriptEngine);
            script = (PopulationScript) se.instantiateClass(null, config.getScript());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean execute(Login login, ProvisionUser user, List<ExtensibleAttribute> attributes) {
        log.debug("Entering CreateIdmAccountCommand");
        if (attributes == null) {
            log.debug("Can't create IDM user without attributes");
        } else {
            Map<String, String> line = new HashMap<String, String>();
            for (ExtensibleAttribute attr : attributes) {
                line.put(attr.getName(), attr.getValue());
            }
            if (script == null) {
                log.debug("Error in Population for user because GroovyScript = " + config.getScript() + " wasn't initialized!");
            }
            ProvisionUser pUser = user != null ? user : new ProvisionUser();
            int retval = script.execute(line, pUser);
            if (retval == 0) {
                log.debug("Population successful for user: " + login.getId());
                login.getId().setManagedSysId("0");
                pUser.getPrincipalList().add(login);
                provisionService.addUser(pUser);
                //provisionService.modifyUser(pUser);
            } else {
                log.debug("Couldn't populate ProvisionUser. User not added");
                return false;
            }
            return true;
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
