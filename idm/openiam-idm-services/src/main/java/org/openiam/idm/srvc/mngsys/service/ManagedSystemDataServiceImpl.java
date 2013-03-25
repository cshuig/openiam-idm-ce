package org.openiam.idm.srvc.mngsys.service;

import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.exception.EncryptionException;
import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.idm.srvc.mngsys.dto.ManagedSystemObjectMatch;
import org.openiam.idm.srvc.mngsys.dto.ApproverAssociation;
import org.openiam.idm.srvc.user.service.UserDataService;
import org.openiam.util.encrypt.Cryptor;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService", 
		targetNamespace = "urn:idm.openiam.org/srvc/mngsys/service", 
		portName = "ManagedSystemWebServicePort",
		serviceName = "ManagedSystemWebService")
public class ManagedSystemDataServiceImpl implements ManagedSystemDataService {

	private ManagedSysDAO managedSysDao;
	private ManagedSystemObjectMatchDAO managedSysObjectMatchDao;
	private ApproverAssociationDAO approverAssociationDao;
	private UserDataService userManager;
	private AttributeMapDAO attributeMapDao;
	

	private static final Log log = LogFactory.getLog(ManagedSystemDataServiceImpl.class);
	
	private Cryptor cryptor;
	static private ResourceBundle res = ResourceBundle.getBundle("securityconf");
	private boolean encrypt = true;	// default encryption setting

    @Transactional
	public ManagedSys addManagedSystem(ManagedSys sys) {

		if (sys == null) {
			throw new NullPointerException("sys is null");
		}
		
        if (encrypt && sys.getPswd() != null) {
        	try {
        		sys.setPswd(cryptor.encrypt(sys.getPswd()));
        	}catch(EncryptionException e) {
        		log.error(e);
        	}
        };
		
		return managedSysDao.add(sys);

	}

    @Transactional(readOnly = true)
	public ManagedSys getManagedSys(String sysId) {
		if (sysId == null) {
			throw new NullPointerException("sysId is null");
		}

		ManagedSys sys = managedSysDao.findById(sysId);
		
		if (sys != null && sys.getPswd() != null) {
			try {
				sys.setDecryptPassword(cryptor.decrypt(sys.getPswd()));
        	}catch(EncryptionException e) {
        		log.error(e);
        	}
		}
		return sys;

	}

    @Transactional(readOnly = true)
	public ManagedSys[] getManagedSysByProvider(String providerId) {
		if (providerId == null) {
			throw new NullPointerException("providerId is null");
		}
		List<ManagedSys> sysList= managedSysDao.findbyConnectorId(providerId);
		if (sysList == null)
			return null;
		int size = sysList.size();
		ManagedSys[] sysAry = new ManagedSys[size];
		sysList.toArray(sysAry);
		return sysAry;
	}
	
	/**
	 * Returns an array of ManagedSys object for a security domain.  
	 * @param domainId
	 * @return
	 */
    @Transactional(readOnly = true)
	public ManagedSys[] getManagedSysByDomain(String domainId) {
		if (domainId == null) {
			throw new NullPointerException("domainId is null");
		}
		List<ManagedSys> sysList= managedSysDao.findbyDomain(domainId);
		if (sysList == null)
			return null;
		int size = sysList.size();
		ManagedSys[] sysAry = new ManagedSys[size];
		sysList.toArray(sysAry);
		return sysAry;
		
	}

    @Transactional(readOnly = true)
	public ManagedSys[] getAllManagedSys() {
		List<ManagedSys> sysList= managedSysDao.findAllManagedSys();
		if (sysList == null)
			return null;
		int size = sysList.size();
		ManagedSys[] sysAry = new ManagedSys[size];
		sysList.toArray(sysAry);
		return sysAry;
	}

    @Transactional
	public void removeManagedSystem(String sysId) {
		if (sysId == null) {
			throw new NullPointerException("sysId is null");
		}
		ManagedSys sys = getManagedSys(sysId);
		managedSysDao.remove(sys);
	}

    @Transactional
	public void updateManagedSystem(ManagedSys sys) {
		if (sys == null) {
			throw new NullPointerException("sys is null");
		}
        if (encrypt && sys.getPswd() != null) {
        	try {
        		sys.setPswd(cryptor.encrypt(sys.getPswd()));
        	}catch(EncryptionException e) {
        		log.error(e);
        	}
        };
        
		managedSysDao.update(sys);
	}
	
	/**
	 * Finds objects for an object type (like User, Group) for a ManagedSystem definition
	 * @param managedSystemId
	 * @param objectType
	 * @return
	 */
    @Transactional(readOnly = true)
	public ManagedSystemObjectMatch[] managedSysObjectParam(String managedSystemId, String objectType) {
		if (managedSystemId == null) {
			throw new NullPointerException("managedSystemId is null");
		}
		if (objectType == null) {
			throw new NullPointerException("objectType is null");
		}
		List<ManagedSystemObjectMatch> objList = managedSysObjectMatchDao.findBySystemId(managedSystemId, objectType);
		if (objList == null) {
			return null;
		}
		int size = objList.size();
		ManagedSystemObjectMatch[] objAry = new ManagedSystemObjectMatch[size];
		objList.toArray(objAry);
		return objAry;
		
		
	}

    @Transactional(readOnly = true)
	public ManagedSys getManagedSysByResource(String resourceId) {
		if (resourceId == null) {
			throw new NullPointerException("resourceId is null");
		}		
		return managedSysDao.findByResource(resourceId, "ACTIVE");
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService#getApproversByAction(java.lang.String, java.lang.String, int)
	 */
/*	public List<ApproverAssociation> getApproversByAction(String managedSysId,
			String action, int level) {
		if ( managedSysId == null) {
			throw new NullPointerException("managedSysId is null");
		}
		return  resourceApproverDao.findApproversByAction(managedSysId, action, level);
	}
*/
	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService#getApproversByResource(java.lang.String)
	 */
/*	public List<ApproverAssociation> getApproversByResource(String managedSysId) {
		if ( managedSysId == null) {
			throw new NullPointerException("managedSysId is null");
		}
		return  resourceApproverDao.findApproversByResource(managedSysId);
	}
*/

	
	public ManagedSysDAO getManagedSysDao() {
		return managedSysDao;
	}

	public void setManagedSysDao(ManagedSysDAO managedSysDao) {
		this.managedSysDao = managedSysDao;
	}

	public ManagedSystemObjectMatchDAO getManagedSysObjectMatchDao() {
		return managedSysObjectMatchDao;
	}

	public void setManagedSysObjectMatchDao(
			ManagedSystemObjectMatchDAO managedSysObjectMatchDao) {
		this.managedSysObjectMatchDao = managedSysObjectMatchDao;
	}

	public Cryptor getCryptor() {
		return cryptor;
	}

	public void setCryptor(Cryptor cryptor) {
		this.cryptor = cryptor;
	}



	public UserDataService getUserManager() {
		return userManager;
	}

	public void setUserManager(UserDataService userManager) {
		this.userManager = userManager;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService#getManagedSysByName(java.lang.String)
	 */
    @Transactional(readOnly = true)
	public ManagedSys getManagedSysByName(String name) {
		if (name == null) {
			throw new NullPointerException("Parameter Managed system name is null");
		}
		return managedSysDao.findByName(name);
	}

	//Approver Association  ================================================================

	
	public ApproverAssociationDAO getApproverAssociationDao() {
		return approverAssociationDao;
	}

	public void setApproverAssociationDao(ApproverAssociationDAO approverAssociationDao) {
		this.approverAssociationDao = approverAssociationDao;
	}

    @Transactional
	public ApproverAssociation addApproverAssociation(ApproverAssociation approverAssociation) {
		if (approverAssociation == null)
			throw new IllegalArgumentException("approverAssociation object is null");

		return approverAssociationDao.add(approverAssociation);
	}

    @Transactional
    public ApproverAssociation updateApproverAssociation(ApproverAssociation approverAssociation) {
		if (approverAssociation == null)
			throw new IllegalArgumentException("approverAssociation object is null");

		return approverAssociationDao.update(approverAssociation);
	}

    @Transactional(readOnly = true)
	public ApproverAssociation getApproverAssociation(String approverAssociationId) {
		if (approverAssociationId == null)
			throw new IllegalArgumentException("approverAssociationId is null");

		return approverAssociationDao.findById(approverAssociationId);
	}

    @Transactional
	public void removeApproverAssociation(String approverAssociationId) {
		if (approverAssociationId == null)
			throw new IllegalArgumentException("approverAssociationId is null");
		ApproverAssociation obj = this.approverAssociationDao.findById(approverAssociationId);
		this.approverAssociationDao.remove(obj);
	}

    @Transactional
	public int removeAllApproverAssociations() {
		return this.approverAssociationDao.removeAllApprovers();
	}

    @Transactional(readOnly = true)
	public List<ApproverAssociation> getApproverByRequestType(String requestType, int level) {
		if (requestType == null)
			throw new IllegalArgumentException("requestType is null");
		return this.approverAssociationDao.findApproversByRequestType(requestType, level);
	}

    @Transactional(readOnly = true)
	public List<ApproverAssociation> getAllApproversByRequestType(String requestType) {
		if (requestType == null)
			throw new IllegalArgumentException("requestType is null");
		return approverAssociationDao.findAllApproversByRequestType(requestType);		
	}

    @Transactional(readOnly = true)
	public List<ApproverAssociation> getApproversByObjectId (String associationObjId) {
		if (associationObjId == null)
			throw new IllegalArgumentException("associationObjId is null");
		return this.approverAssociationDao.findApproversByObjectId(associationObjId);
	}

    @Transactional
	public int removeApproversByObjectId(String associationObjId) {
		if (associationObjId == null)
			throw new IllegalArgumentException("associationObjId is null");
		return this.approverAssociationDao.removeApproversByObjectId(associationObjId);
	}

	// find by RESOURCE, GROUP, ROLE, SUPERVISOR,INDIVIDUAL
    @Transactional(readOnly = true)
    List<ApproverAssociation> getApproversByObjectType(String associationType) {
		if (associationType == null)
			throw new IllegalArgumentException("associationType is null");
		return this.approverAssociationDao.findApproversByObjectType(associationType);
	}

    @Transactional
	public int removeApproversByObjectType(String associationType) {
		if (associationType == null)
			throw new IllegalArgumentException("associationType is null");
		return this.approverAssociationDao.removeApproversByObjectType(associationType);
	}

    @Transactional(readOnly = true)
	List<ApproverAssociation> getApproversByAction(String associationObjId,
			String action, int level) {
		if (associationObjId == null)
			throw new IllegalArgumentException("associationObjId is null");
		return this.approverAssociationDao.findApproversByAction(associationObjId, action, level);
	}

    @Transactional(readOnly = true)
	List<ApproverAssociation> getApproversByUser(String userId) {
		if (userId == null)
			throw new IllegalArgumentException("userId is null");
		return this.approverAssociationDao.findApproversByUser(userId);
	}

    @Transactional
	public int removeApproversByUser(String userId) {
		if (userId == null)
			throw new IllegalArgumentException("userId is null");
		return this.approverAssociationDao.removeApproversByUser(userId);
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService#addManagedSystemObjectMatch(org.openiam.idm.srvc.mngsys.dto.ManagedSystemObjectMatch)
	 */
    @Transactional
	public void addManagedSystemObjectMatch(ManagedSystemObjectMatch obj) {
		managedSysObjectMatchDao.add(obj);
		
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService#updateManagedSystemObjectMatch(org.openiam.idm.srvc.mngsys.dto.ManagedSystemObjectMatch)
	 */
    @Transactional
	public void updateManagedSystemObjectMatch(ManagedSystemObjectMatch obj) {
		this.managedSysObjectMatchDao.update(obj);
		
	}

    @Transactional
	public void removeManagedSystemObjectMatch(ManagedSystemObjectMatch obj) {
		this.managedSysObjectMatchDao.remove(obj);
	}

    @Transactional(readOnly = true)
	public AttributeMap getAttributeMap(String attributeMapId) {
		if (attributeMapId == null)
			throw new IllegalArgumentException("attributeMapId is null");

		AttributeMap obj = attributeMapDao.findById(attributeMapId);

		return obj;
	}

    @Transactional
	public AttributeMap addAttributeMap(AttributeMap attributeMap) {
		if (attributeMap == null)
			throw new IllegalArgumentException("AttributeMap object is null");

		return attributeMapDao.add(attributeMap);
	}

    @Transactional
	public AttributeMap updateAttributeMap(AttributeMap attributeMap) {
		if (attributeMap == null)
			throw new IllegalArgumentException("attributeMap object is null");

		return attributeMapDao.update(attributeMap);
	}

    @Transactional
	public void removeAttributeMap(String attributeMapId) {
		if (attributeMapId == null) {
			throw new IllegalArgumentException("attributeMapId is null");
		}
		AttributeMap obj = this.attributeMapDao.findById(attributeMapId);
		this.attributeMapDao.remove(obj);
		
	}

    @Transactional
	public int removeResourceAttributeMaps(String resourceId) {
		if (resourceId == null)
			throw new IllegalArgumentException("resourceId is null");

		return this.attributeMapDao.removeResourceAttributeMaps(resourceId);
	}

    @Transactional(readOnly = true)
	public List<AttributeMap> getResourceAttributeMaps(String resourceId) {
		if (resourceId == null) {
			throw new IllegalArgumentException("resourceId is null");
		}
		return attributeMapDao.findByResourceId(resourceId);
	}

    @Transactional(readOnly = true)
	public List<AttributeMap> getAllAttributeMaps() {
		List<AttributeMap> attributeMapList = attributeMapDao.findAllAttributeMaps();

		return attributeMapList;
	}

    public AttributeMapDAO getAttributeMapDao() {
		return attributeMapDao;
	}

	public void setAttributeMapDao(AttributeMapDAO attributeMapDao) {
		this.attributeMapDao = attributeMapDao;
	}
	
}
