package org.openiam.ui.webconsole.idm.web.mvc.provisioning.connectors;

import org.hibernate.validator.constraints.NotBlank;
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnectorDto;

public class ConnectorCommand {

    private String connectorId;
    @NotBlank
    private String name;
    private String metadataTypeId;
    @NotBlank
    private String clientCommProtocol;
    private String connectorInterface;
    private String serviceUrl;
    private String serviceNameSpace;
    private String servicePort;
    private String submitType;

    public ConnectorCommand() {
    }

    public ConnectorCommand(ProvisionConnectorDto connector) {
        this.connectorId = connector.getConnectorId();
        this.name = connector.getName();
        this.metadataTypeId = connector.getMetadataTypeId();
        this.clientCommProtocol = connector.getClientCommProtocol();
        this.connectorInterface = connector.getConnectorInterface();
        this.serviceUrl = connector.getServiceUrl();
        this.serviceNameSpace = connector.getServiceNameSpace();
        this.servicePort = connector.getServicePort();
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetadataTypeId() {
        return metadataTypeId;
    }

    public void setMetadataTypeId(String metadataTypeId) {
        this.metadataTypeId = metadataTypeId;
    }

    public String getClientCommProtocol() {
        return clientCommProtocol;
    }

    public void setClientCommProtocol(String clientCommProtocol) {
        this.clientCommProtocol = clientCommProtocol;
    }

    public String getConnectorInterface() {
        return connectorInterface;
    }

    public void setConnectorInterface(String connectorInterface) {
        this.connectorInterface = connectorInterface;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceNameSpace() {
        return serviceNameSpace;
    }

    public void setServiceNameSpace(String serviceNameSpace) {
        this.serviceNameSpace = serviceNameSpace;
    }

    public String getServicePort() {
        return servicePort;
    }

    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }

    public ProvisionConnectorDto getProvisionConnectorDto() {
        ProvisionConnectorDto connectorDto = new ProvisionConnectorDto();
        connectorDto.setConnectorId(this.connectorId);
        connectorDto.setName(this.name);
        connectorDto.setClientCommProtocol(this.clientCommProtocol);
        connectorDto.setConnectorInterface(this.connectorInterface);
        connectorDto.setMetadataTypeId(this.metadataTypeId);
        connectorDto.setServiceUrl(this.serviceUrl);
        connectorDto.setServicePort(this.servicePort);
        connectorDto.setServiceNameSpace(this.serviceNameSpace);
        return connectorDto;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }
}
