package org.openiam.ui.webconsole.idm.web.mvc.provisioning.synchronization;

import org.openiam.ui.web.model.PaginationCommand;

public class SynchReviewListCommand extends PaginationCommand {

    private String synchConfigId;

    public String getSynchConfigId() {
        return synchConfigId;
    }

    public void setSynchConfigId(String synchConfigId) {
        this.synchConfigId = synchConfigId;
    }
}
