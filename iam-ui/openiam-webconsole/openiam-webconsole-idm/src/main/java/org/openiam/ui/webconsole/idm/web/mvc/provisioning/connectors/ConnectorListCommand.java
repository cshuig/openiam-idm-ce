package org.openiam.ui.webconsole.idm.web.mvc.provisioning.connectors;

import org.openiam.ui.web.model.PaginationCommand;

public class ConnectorListCommand extends PaginationCommand {
    private String connectorTypeId;
    private String connectorName;

    public ConnectorListCommand() {
    }

    public String getConnectorTypeId() {
        return connectorTypeId;
    }

    public void setConnectorTypeId(String connectorTypeId) {
        this.connectorTypeId = connectorTypeId;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }
}
