package org.openiam.ui.webconsole.idm.web.mvc.provisioning.mngsystems;

import org.openiam.ui.web.model.PaginationCommand;

public class MngSystemListCommand extends PaginationCommand {
    private String domainId;
    private String name;

    public MngSystemListCommand() {
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
