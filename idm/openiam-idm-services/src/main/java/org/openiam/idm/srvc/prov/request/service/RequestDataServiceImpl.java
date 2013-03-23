package org.openiam.idm.srvc.prov.request.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.mngsys.dto.ApproverAssociation;
import org.openiam.idm.srvc.mngsys.service.ApproverAssociationDAO;
import org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService;
import org.openiam.idm.srvc.msg.service.MailService;
import org.openiam.idm.srvc.prov.request.dto.ProvisionRequest;
import org.openiam.idm.srvc.prov.request.dto.RequestApprover;
import org.openiam.idm.srvc.prov.request.dto.SearchRequest;
import org.openiam.idm.srvc.user.dto.Supervisor;
import org.openiam.idm.srvc.user.service.UserDataService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Service implementation to manage provisioning requests
 */
public class RequestDataServiceImpl implements RequestDataService {
    private static final Log log = LogFactory.getLog(RequestDataServiceImpl.class);


    private ProvisionRequestDAO requestDao;
    private ManagedSystemDataService managedResource;
    private ApproverAssociationDAO approverAssociationDao;
    private UserDataService userManager;
    private MailService mailSender;

    private String defaultSender;
    private String subjectPrefix;

    @Transactional
    public void addRequest(ProvisionRequest request) {
        if (request == null) {
            throw new NullPointerException("request is null");
        }
        requestDao.add(request);

    }

    @Transactional(readOnly = true)
    public ProvisionRequest getRequest(String requestId) {
        if (requestId == null) {
            throw new NullPointerException("requestId is null");
        }
        return requestDao.findById(requestId);
    }

    @Transactional
    public void removeRequest(String requestId) {
        if (requestId == null) {
            throw new NullPointerException("requestId is null");
        }
        ProvisionRequest request = new ProvisionRequest();
        request.setRequestId(requestId);
        requestDao.remove(request);

    }

    @Transactional(readOnly = true)
    public List<ProvisionRequest> search(SearchRequest search) {

        log.info("Request:search operation called.");

        List<ProvisionRequest> reqList = requestDao.search(search);
        if (reqList == null || reqList.size() == 0)
            return null;

        log.info("Request:search found records=" + reqList.size());

        return reqList;
    }

    @Transactional(readOnly = true)
    public List<ProvisionRequest> requestByApprover(String approverId, String status) {
        List<ProvisionRequest> reqList = requestDao.findRequestByApprover(approverId, status);
        if (reqList == null || reqList.size() == 0) {
            return null;
        }
        return reqList;
    }

    @Transactional
    public void setRequestStatus(String requestId, String approverId, String status) {
        if (requestId == null) {
            throw new NullPointerException("requestId is null");
        }
        if (approverId == null) {
            throw new NullPointerException("userId is null");
        }
        if (status == null) {
            throw new NullPointerException("status is null");
        }
        ProvisionRequest request = requestDao.findById(requestId);
        request.setStatus(status);
        request.setStatusDate(new Date(System.currentTimeMillis()));


        requestDao.update(request);
    }

    @Transactional
    public void updateRequest(ProvisionRequest request) {
        if (request == null) {
            throw new NullPointerException("request is null");
        }
        requestDao.update(request);
    }

    public ProvisionRequestDAO getRequestDao() {
        return requestDao;
    }

    public void setRequestDao(ProvisionRequestDAO requestDao) {
        this.requestDao = requestDao;
    }


    /* (non-Javadoc)
      * @see org.openiam.idm.srvc.prov.request.service.RequestDataService#approve()
      */
    @Transactional
    public void approve(String requestId) {
        ProvisionRequest req = getRequest(requestId);
        req.setStatus("APPROVED");
        updateRequest(req);

    }

    @Transactional(readOnly = true)
    private Set<RequestApprover> getApprover(List<ApproverAssociation> approverList, Supervisor supervisor) {
        Set<RequestApprover> reqApproverList = new HashSet<RequestApprover>();

        // look at the first approver to figure the type of approver
        if (approverList == null || approverList.isEmpty()) {
            return null;
        }
        ApproverAssociation approver = approverList.get(0);
        String assocType = approver.getAssociationType();
        if (assocType == null) {
            throw new IllegalArgumentException("Approver association is not defined.");
        }


        if (assocType.equalsIgnoreCase("SUPERVISOR")) {

            String supervisorUserId = supervisor.getSupervisor().getUserId();
            RequestApprover app = new RequestApprover();

            app.setApproverId(supervisorUserId);
            app.setApproverType("SUPERVISOR");
            app.setApproverLevel(approver.getApproverLevel());
            reqApproverList.add(app);
            return reqApproverList;
        }
        if (assocType.equalsIgnoreCase("GROUP") ||
                assocType.equalsIgnoreCase("ROLE")) {

            for (ApproverAssociation assoc : approverList) {
                RequestApprover app = new RequestApprover();
                app.setApproverType(assocType);
                app.setApproverId(assoc.getApproverUserId());
                app.setApproverLevel(app.getApproverLevel());
                reqApproverList.add(app);
            }

            return reqApproverList;
        }

        return null;

    }


    /* (non-Javadoc)
      * @see org.openiam.idm.srvc.prov.request.service.RequestDataService#reject()
      */
    @Transactional
    public void reject(String requestId) {
        ProvisionRequest req = getRequest(requestId);
        req.setStatus("REJECTED");
        updateRequest(req);

    }

    public ManagedSystemDataService getManagedResource() {
        return managedResource;
    }

    public void setManagedResource(ManagedSystemDataService managedResource) {
        this.managedResource = managedResource;
    }

    public UserDataService getUserManager() {
        return userManager;
    }

    public void setUserManager(UserDataService userManager) {
        this.userManager = userManager;
    }


    public String getDefaultSender() {
        return defaultSender;
    }

    public void setDefaultSender(String defaultSender) {
        this.defaultSender = defaultSender;
    }

    public String getSubjectPrefix() {
        return subjectPrefix;
    }

    public void setSubjectPrefix(String subjectPrefix) {
        this.subjectPrefix = subjectPrefix;
    }

    public MailService getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailService mailSender) {
        this.mailSender = mailSender;
    }

    public ApproverAssociationDAO getApproverAssociationDao() {
        return approverAssociationDao;
    }

    public void setApproverAssociationDao(
            ApproverAssociationDAO approverAssociationDao) {
        this.approverAssociationDao = approverAssociationDao;
    }


}
