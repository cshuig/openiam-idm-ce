package org.openiam.idm.srvc.mngsys.service;

import org.openiam.idm.srvc.mngsys.dto.ProvisionConnector;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.jws.WebService;

/**
 * Service layer implementation to manage the provisioning connectors in OpenIAM. 
 */
@WebService(endpointInterface = "org.openiam.idm.srvc.mngsys.service.ConnectorDataService", 
		targetNamespace = "urn:idm.openiam.org/srvc/mngsys/service", 
		portName = "ConnectorWebServicePort",
		serviceName = "ConnectorWebService")
public class ConnectorDataServiceImpl implements ConnectorDataService {
	private ProvisionConnectorDAO connectorDao =null;
	
	public ProvisionConnectorDAO getConnectorDao() {
		return connectorDao;
	}

	public void setConnectorDao(ProvisionConnectorDAO connectorDao) {
		this.connectorDao = connectorDao;
	}

    @Transactional
	public void addConnector(ProvisionConnector con) {
		if (con == null) {
			throw new NullPointerException("Connector object cannot be null");
		}
		connectorDao.add(con);

	}

    @Transactional(readOnly = true)
	public ProvisionConnector[] getAllConnectors() {
	 	List<ProvisionConnector> conList = connectorDao.findAllConnectors();
	 	if (conList != null && conList.size() > 0) {
	 		int size = conList.size();
	 		ProvisionConnector[] conAry = new ProvisionConnector[size];
	 		conList.toArray(conAry);
	 		return conAry;
	 	}
		return null;
	}

    @Transactional(readOnly = true)
	public ProvisionConnector getConnector(String conId) {
		if (conId == null) {
			throw new NullPointerException("ConnectorId cannot be null");
		}
		return connectorDao.findById(conId);
	}

    @Transactional(readOnly = true)
	public ProvisionConnector[] getConnectorsByType(String typeId) {
		if (typeId == null) {
			throw new NullPointerException("typeId cannot be null");
		}
		List<ProvisionConnector> conList = connectorDao.findConnectorByType(typeId);
	 	if (conList != null && conList.size() > 0) {
	 		int size = conList.size();
	 		ProvisionConnector[] conAry = new ProvisionConnector[size];
	 		conList.toArray(conAry);
	 		return conAry;
	 	}
		return null;
	}

    @Transactional
	public void removeConnector(String conId) {
		if (conId == null) {
			throw new NullPointerException("ConnectorId cannot be null");
		}
		ProvisionConnector con = new ProvisionConnector(conId);
		connectorDao.remove(con);
	}

    @Transactional
	public void updateConnector(ProvisionConnector con) {
		if (con == null) {
			throw new NullPointerException("Connector object cannot be null");
		}
		connectorDao.update(con);
	}

} 
