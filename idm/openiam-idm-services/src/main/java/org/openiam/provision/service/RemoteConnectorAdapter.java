/*
 * Copyright 2009, OpenIAM LLC 
 * This file is part of the OpenIAM Identity and Access Management Suite
 *
 *   OpenIAM Identity and Access Management Suite is free software: 
 *   you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License 
 *   version 3 as published by the Free Software Foundation.
 *
 *   OpenIAM is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   Lesser GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenIAM.  If not, see <http://www.gnu.org/licenses/>. *
 */

/**
 *
 */
package org.openiam.provision.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.openiam.connector.type.*;
import org.openiam.connector.type.ResponseType;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnector;
import org.openiam.idm.srvc.mngsys.service.ConnectorDataService;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.spml2.interf.ConnectorService;
import org.openiam.spml2.msg.*;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;


/**
 * Wraps around the connector interface and manages the calls to the varous operations for the
 * connectors for provisioning.
 *
 * @author suneet
 */
public class RemoteConnectorAdapter {

    protected static final Log log = LogFactory.getLog(RemoteConnectorAdapter.class);
    public static final String AD_ERROR_THIS_IS_LIKELY_A_TRANSIENT_CONDITION = "This is likely a transient condition";

    protected ConnectorDataService connectorService;
    private final static int RESEND_COUNT = 3;

    public UserResponse addRequest(ManagedSys managedSys, RemoteUserRequest addReqType, ProvisionConnector connector, MuleContext muleContext) {
        UserResponse resp = new UserResponse();
        try {

            if (managedSys == null) {
                resp.setStatus(StatusCodeType.FAILURE);
                resp.setError(ErrorCode.INVALID_MANAGED_SYS_ID);
                return resp;
            }
            log.debug("ConnectorAdapter:addRequest called. Managed sys =" + managedSys.getManagedSysId());

            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {
                int i = 0;
                resp = send("add", addReqType, connector, muleContext);
                // Check if response has error = "A connection to the directory on which to process the request was unavailable. This is likely a transient condition."
                // we try to resend request RESEND_COUNT times
                while((resp.getStatus() == StatusCodeType.FAILURE && resp.getErrorMsgAsStr().toLowerCase().contains(AD_ERROR_THIS_IS_LIKELY_A_TRANSIENT_CONDITION.toLowerCase()))
                        && i < RESEND_COUNT) {
                    Thread.sleep(200);
                    resp = send("add", addReqType, connector, muleContext);
                    i++;
                }
                return resp;
            }

        } catch (Exception e) {
            log.error(e);
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;


    }

    private UserResponse send(String eventName, UserRequest request, ProvisionConnector connector, MuleContext muleContext){
        UserResponse resp = new UserResponse();
        MuleMessage msg = getService(connector, request, connector.getServiceUrl(), eventName, muleContext);
        if (msg != null) {
            log.debug("***Payload=" + msg.getPayload());
            if (msg.getPayload() != null && msg.getPayload() instanceof UserResponse) {
                return (UserResponse) msg.getPayload();
            }
            resp.setStatus(StatusCodeType.SUCCESS);
            return resp;
        } else {
            log.debug("MuleMessage is null..");
        }
        return resp;
    }
    private LookupResponse sendLookup(String eventName, LookupRequest request, ProvisionConnector connector, MuleContext muleContext){
        LookupResponse resp = new LookupResponse();
        MuleMessage msg = getService(connector, request, connector.getServiceUrl(), eventName, muleContext);
        if (msg != null) {
            log.debug("***Payload=" + msg.getPayload());
            if (msg.getPayload() != null && msg.getPayload() instanceof UserResponse) {
                return (LookupResponse) msg.getPayload();
            }
            resp.setStatus(StatusCodeType.SUCCESS);
            return resp;
        } else {
            log.debug("MuleMessage is null..");
        }
        return resp;
    }

    public UserResponse modifyRequest(ManagedSys managedSys, RemoteUserRequest request, ProvisionConnector connector, MuleContext muleContext) {
        UserResponse resp = new UserResponse();
        try {
            if (managedSys == null) {
                resp.setStatus(StatusCodeType.FAILURE);
                resp.setError(ErrorCode.INVALID_MANAGED_SYS_ID);
                return resp;
            }
            log.debug("ConnectorAdapter:modifyRequest called. Managed sys =" + managedSys.getManagedSysId());

            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {
                int i = 0;
                resp = send("modify", request, connector, muleContext);
                // Check if response has error = "A connection to the directory on which to process the request was unavailable. This is likely a transient condition."
                // we try to resend request RESEND_COUNT times
                while((resp.getStatus() == StatusCodeType.FAILURE && resp.getErrorMsgAsStr().toLowerCase().contains(AD_ERROR_THIS_IS_LIKELY_A_TRANSIENT_CONDITION.toLowerCase()))
                        && i < RESEND_COUNT) {
                    Thread.sleep(200);
                    resp = send("modify", request, connector, muleContext);
                    i++;
                }
                return resp;
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;


    }

    public LookupResponse lookupRequest(ManagedSys managedSys, RemoteLookupRequest req, ProvisionConnector connector, MuleContext muleContext) {

        LookupResponse resp = new LookupResponse();

        if (managedSys == null) {
            resp.setStatus(StatusCodeType.FAILURE);
            resp.setError(ErrorCode.INVALID_MANAGED_SYS_ID);
            return resp;
        }
        log.debug("ConnectorAdapter:lookupRequest called. Managed sys =" + managedSys.getManagedSysId());

        try {

            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {
                int i = 0;
                resp = sendLookup("lookup", req, connector, muleContext);
                // Check if response has error = "A connection to the directory on which to process the request was unavailable. This is likely a transient condition."
                // we try to resend request RESEND_COUNT times
                while((resp.getStatus() == StatusCodeType.FAILURE && resp.getErrorMsgAsStr().toLowerCase().contains(AD_ERROR_THIS_IS_LIKELY_A_TRANSIENT_CONDITION.toLowerCase()))
                        && i < RESEND_COUNT) {
                    Thread.sleep(200);
                    resp = sendLookup("lookup", req, connector, muleContext);
                    i++;
                }
                return resp;
            }
        } catch (Exception e) {
            log.error(e);
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;

    }

    public UserResponse deleteRequest(ManagedSys managedSys, RemoteUserRequest request, ProvisionConnector connector, MuleContext muleContext) {
        UserResponse resp = new UserResponse();

        if (managedSys == null) {
            resp.setStatus(StatusCodeType.FAILURE);
            resp.setError(ErrorCode.INVALID_MANAGED_SYS_ID);
            return resp;
        }

        log.debug("ConnectorAdapter:deleteRequest called. Managed sys =" + managedSys.getManagedSysId());

        try {

            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {

                MuleMessage msg = getService(connector, request, connector.getServiceUrl(), "delete", muleContext);
                if (msg != null) {
                    log.debug("***Payload=" + msg.getPayload());
                    if (msg.getPayload() != null && msg.getPayload() instanceof UserResponse) {
                        return (UserResponse) msg.getPayload();
                    }
                    resp.setStatus(StatusCodeType.SUCCESS);
                    return resp;
                } else {
                    log.debug("MuleMessage is null..");
                }


            }
        } catch (Exception e) {
            log.error(e);
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;

    }

    public ResponseType setPasswordRequest(ManagedSys managedSys, RemotePasswordRequest request, ProvisionConnector connector, MuleContext muleContext) {
        ResponseType resp = new ResponseType();

        if (managedSys == null) {
            resp.setStatus(StatusCodeType.FAILURE);
            resp.setError(ErrorCode.INVALID_MANAGED_SYS_ID);
            return resp;
        }

        log.debug("ConnectorAdapter:setPasswordRequest called. Managed sys =" + managedSys.getManagedSysId());
        try {

            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {

                MuleMessage msg = getService(connector, request, connector.getServiceUrl(), "setPassword", muleContext);
                if (msg != null) {
                    log.debug("***Payload=" + msg.getPayload());
                    if (msg.getPayload() != null && msg.getPayload() instanceof ResponseType) {
                        return (ResponseType) msg.getPayload();
                    }
                    resp.setStatus(StatusCodeType.SUCCESS);
                    return resp;
                } else {
                    log.debug("MuleMessage is null..");
                }


            }
        } catch (Exception e) {
            log.error(e);
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;


    }

    public ResponseType resetPasswordRequest(ManagedSys managedSys, RemotePasswordRequest request, ProvisionConnector connector, MuleContext muleContext) {
        ResponseType resp = new ResponseType();

        if (managedSys == null) {
            resp.setStatus(StatusCodeType.FAILURE);
            resp.setError(ErrorCode.INVALID_MANAGED_SYS_ID);
            return resp;
        }
        log.debug("ConnectorAdapter:resetPasswordRequest called. Managed sys =" + managedSys.getManagedSysId());

        try {
            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {

                MuleMessage msg = getService(connector, request, connector.getServiceUrl(), "resetPassword", muleContext);
                if (msg != null) {
                    log.debug("***Payload=" + msg.getPayload());
                    if (msg.getPayload() != null && msg.getPayload() instanceof ResponseType) {
                        return (ResponseType) msg.getPayload();
                    }
                    resp.setStatus(StatusCodeType.SUCCESS);
                    return resp;
                } else {
                    log.debug("MuleMessage is null..");
                }


                //ConnectorService port = getService(connector);
                //port.resetPassword(request);

            }
        } catch (Exception e) {
            log.error(e);
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;


    }

    public ResponseType suspend(ManagedSys managedSys, SuspendRequest request, ProvisionConnector connector, MuleContext muleContext) {
        ResponseType resp = new ResponseType();

        if (managedSys == null) {
            resp.setStatus(StatusCodeType.FAILURE);
            resp.setError(ErrorCode.INVALID_MANAGED_SYS_ID);
            return resp;
        }

        log.debug("ConnectorAdapter:suspendRequest called. Managed sys =" + managedSys.getManagedSysId());

        try {

            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {

                MuleMessage msg = getService(connector, request, connector.getServiceUrl(), "suspend", muleContext);
                if (msg != null) {
                    log.debug("***Payload=" + msg.getPayload());
                    if (msg.getPayload() != null && msg.getPayload() instanceof ResponseType) {
                        return (ResponseType) msg.getPayload();
                    }
                    resp.setStatus(StatusCodeType.SUCCESS);
                    return resp;
                } else {
                    log.debug("MuleMessage is null..");
                }

                //ConnectorService port = getService(connector);
                //port.suspend(request);

            }
        } catch (Exception e) {
            log.error(e);
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;


    }

    public ResponseType resumeRequest(ManagedSys managedSys, ResumeRequest request, ProvisionConnector connector, MuleContext muleContext) {
        ResponseType resp = new ResponseType();

        if (managedSys == null) {
            resp.setStatus(StatusCodeType.FAILURE);
            resp.setError(ErrorCode.INVALID_MANAGED_SYS_ID);
            return resp;
        }

        log.debug("ConnectorAdapter:resumeRequest called. Managed sys =" + managedSys.getManagedSysId());
        try {


            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {

                MuleMessage msg = getService(connector, request, connector.getServiceUrl(), "resume", muleContext);
                if (msg != null) {
                    log.debug("***Payload=" + msg.getPayload());
                    if (msg.getPayload() != null && msg.getPayload() instanceof ResponseType) {
                        return (ResponseType) msg.getPayload();
                    }
                    resp.setStatus(StatusCodeType.SUCCESS);
                    return resp;
                } else {
                    log.debug("MuleMessage is null..");
                }


                //ConnectorService port = getService(connector);
                //port.resume(request);

            }
        } catch (Exception e) {
            log.error(e);
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;


    }

    public ResponseType testConnection(ManagedSys managedSys,ProvisionConnector connector, MuleContext muleContext) {
        ResponseType resp = new ResponseType();

        if (managedSys == null) {
            resp.setStatus(StatusCodeType.FAILURE);
            resp.setError(ErrorCode.INVALID_MANAGED_SYS_ID);
            return resp;
        }

        log.debug("ConnectorAdapter:resumeRequest called. Managed sys =" + managedSys.getManagedSysId());
        try {


            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {

                MuleMessage msg = getService(connector, managedSys, connector.getServiceUrl(), "testConnection", muleContext);
                if (msg != null) {
                    log.debug("Test connection Payload=" + msg.getPayload());
                    if (msg.getPayload() != null && msg.getPayload() instanceof ResponseType) {
                        return (ResponseType) msg.getPayload();
                    }
                    resp.setStatus(StatusCodeType.SUCCESS);
                    return resp;
                } else {
                    log.debug("MuleMessage is null..");
                }

            }
        } catch (Exception e) {
            log.error(e);
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;

    }

    public SearchResponse search(SearchRequest searchRequest, ProvisionConnector connector, MuleContext muleContext) {
        SearchResponse resp = new SearchResponse();
        if (searchRequest == null) {
            resp.setStatus(StatusCodeType.FAILURE);
            resp.setError(ErrorCode.INVALID_CONFIGURATION);
            return resp;
        }
        log.debug("ConnectorAdapter:reconcileRequest called. Resource =" + searchRequest.getSearchQuery());
        try {
            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {
                //Send search to Remote Connector to get data (e.g. Active Directory via PowershellConnector)
                MuleMessage msg = getService(connector, searchRequest, connector.getServiceUrl(), "search", muleContext);
                if (msg != null) {
                    log.debug("Test connection Payload=" + msg.getPayload());
                    if (msg.getPayload() != null && msg.getPayload() instanceof ResponseType) {
                        resp = (SearchResponse) msg.getPayload();
                        if(resp.getStatus() == StatusCodeType.SUCCESS) {
                              resp.setStatus(StatusCodeType.SUCCESS);
                            return resp;
                        }
                    }
                    resp.setStatus(StatusCodeType.FAILURE);
                    return resp;
                } else {
                    log.debug("MuleMessage is null..");
                }

            }
        } catch (Exception e) {
            log.error(e);
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;
    }

    public ResponseType reconcileResource(RemoteReconciliationConfig config, ProvisionConnector connector, MuleContext muleContext){
        ResponseType resp = new ResponseType();
        if (config == null) {
            resp.setStatus(StatusCodeType.FAILURE);
            resp.setError(ErrorCode.INVALID_CONFIGURATION);
            return resp;
        }
        log.debug("ConnectorAdapter:reconcileRequest called. Resource =" + config.getResourceId());
        try {


            if (connector != null && (connector.getServiceUrl() != null && connector.getServiceUrl().length() > 0)) {

                MuleMessage msg = getService(connector, config, connector.getServiceUrl(), "reconcile", muleContext);
                if (msg != null) {
                    log.debug("Test connection Payload=" + msg.getPayload());
                    if (msg.getPayload() != null && msg.getPayload() instanceof ResponseType) {
                        return (ResponseType) msg.getPayload();
                    }
                    resp.setStatus(StatusCodeType.SUCCESS);
                    return resp;
                } else {
                    log.debug("MuleMessage is null..");
                }

            }
        } catch (Exception e) {
            log.error(e);
        }
        resp.setStatus(StatusCodeType.FAILURE);
        return resp;
    }

    private MuleMessage getService(ProvisionConnector connector, Object reqType, String url, String operation, MuleContext muleContext) {
        try {


            MuleClient client = new MuleClient(muleContext);

            //Map<?,?> msgPropMap = Collections.singletonMap("serviceName","LDAPConnectorService");
            Map<String, String> msgPropMap = new HashMap<String, String>();
            msgPropMap.put("serviceName", url);

            MuleMessage msg = null;

            if (operation.equalsIgnoreCase("add")) {

                msg = client.send("vm://remoteConnectorMessageAdd", (UserRequest) reqType, msgPropMap);
            }
            if (operation.equalsIgnoreCase("modify")) {

                msg = client.send("vm://remoteConnectorMessageModify", (UserRequest) reqType, msgPropMap);
            }
            if (operation.equalsIgnoreCase("lookup")) {

                msg = client.send("vm://remoteConnectorMessageLookup", (LookupRequest) reqType, msgPropMap);
            }
            if (operation.equalsIgnoreCase("reconcile")) {

                client.sendAsync("vm://remoteConnectorMessageReconcile", (ReconciliationConfig) reqType, msgPropMap);
            }
            if (operation.equalsIgnoreCase("delete")) {

                msg = client.send("vm://remoteConnectorMessageDelete", (UserRequest) reqType, msgPropMap);
            }
            if (operation.equalsIgnoreCase("setPassword")) {

                msg = client.send("vm://remoteConnectorMessageSetPassword", (PasswordRequest) reqType, msgPropMap);
            }

            if (operation.equalsIgnoreCase("resetPassword")) {

                msg = client.send("vm://remoteConnectorMessageResetPassword", (PasswordRequest) reqType, msgPropMap);
            }
            if (operation.equalsIgnoreCase("suspend")) {

                msg = client.send("vm://remoteConnectorMessageSuspend", (SuspendRequest) reqType, msgPropMap);
            }
            if (operation.equalsIgnoreCase("resume")) {

                msg = client.send("vm://remoteConnectorMessageResume", (ResumeRequest) reqType, msgPropMap);
            }
            if (operation.equalsIgnoreCase("testConnection")) {

                msg = client.send("vm://remoteConnectorMsgTestConnection", (ManagedSys) reqType, msgPropMap);
            }
            if (operation.equalsIgnoreCase("search")) {

                msg = client.send("vm://remoteConnectorMessageSearch", (SearchRequest) reqType, msgPropMap);
            }

            return msg;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    private ConnectorService getService(ProvisionConnector connector) {
        try {

            QName SERVICE_NAME = new QName(connector.getServiceUrl());
            QName PORT_NAME = new QName(connector.getServiceNameSpace(), connector.getServicePort());

            Service service = Service.create(SERVICE_NAME);
            service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, connector.getServiceUrl());

            ConnectorService port = service.getPort(new QName(connector.getServiceNameSpace(),
                    connector.getServicePort()),
                    ConnectorService.class);
            return port;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public ConnectorDataService getConnectorService() {
        return connectorService;
    }

    public void setConnectorService(ConnectorDataService connectorService) {
        this.connectorService = connectorService;
    }


}
