package org.openiam.ui.webconsole.web.model;

import org.openiam.ui.web.model.PaginationCommand;

import java.util.ArrayList;
import java.util.List;

public class MngSysViewerListCommand extends PaginationCommand {

    List<MngSysViewerCommand> identities = new ArrayList<MngSysViewerCommand>();

    public List<MngSysViewerCommand> getIdentities() {
        return identities;
    }

    public void setIdentities(List<MngSysViewerCommand> identities) {
        this.identities = identities;
    }
}
