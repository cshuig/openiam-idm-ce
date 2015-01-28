package org.openiam.ui.webconsole.web.model;

import org.openiam.ui.web.model.PaginationCommand;

public class AttributePolicyCommand extends PaginationCommand {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
