package org.openiam.ui.webconsole.idm.web.mvc.provisioning.synchronization;

import org.openiam.ui.web.model.PaginationCommand;

public class SynchronizationListCommand extends PaginationCommand {

    private String name;
    private String synchType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynchType() {
        return synchType;
    }

    public void setSynchType(String synchType) {
        this.synchType = synchType;
    }
}
