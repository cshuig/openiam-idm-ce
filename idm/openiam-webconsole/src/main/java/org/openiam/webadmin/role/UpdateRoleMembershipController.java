package org.openiam.webadmin.role;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.audit.ws.AsynchIdmAuditLogWebService;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.res.dto.ResourceRole;
import org.openiam.idm.srvc.res.dto.ResourceRoleId;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Displays a list of locations.
 * @author suneet
 *
 */
public class UpdateRoleMembershipController extends AbstractController {


	protected static final Log log = LogFactory.getLog(UpdateRoleMembershipController.class);
	protected String successView;
    protected ResourceDataService resourceDataService;
    protected AsynchIdmAuditLogWebService auditService;



	public UpdateRoleMembershipController() {
		super();
	}


    /**
     *
     * @param action - ADD or REMOVE
     * @param objectId  - ID of the resource that is the to be added or removed
     * @param domain - domain of the role
     * @param role - role Id
     * @return
     */
    private ModelAndView updateResource(HttpServletRequest request, String action, String objectId, String domain, String role) {
        StringBuilder returnUrl = new StringBuilder( "/roleResource.cnt?menuid=ROLE_RESMAP&menugrp=SECURITY_ROLE&mode=1&resType=");


        String userId = (String)request.getSession().getAttribute("userId");
        String domainId = (String)request.getSession().getAttribute("domainid");
        String login = (String)request.getSession().getAttribute("login");

        Resource res =  resourceDataService.getResource(objectId);

        returnUrl.append(res.getResourceType().getResourceTypeId());


        ResourceRole r = getResourceRole(res, role ,domain);


        if ("ADD".equalsIgnoreCase(action)) {

            resourceDataService.addResourceRole(r);

            logEvent("MODIFY", domainId, login,
                    "WEBCONSOLE", userId, "0", "ROLE", role,
                    "SUCCESS", "ADD RESOURCE",
                    res.getResourceId(), null,
                    "ADD RESOURCE TO " + role + "-" + domain, request.getRemoteHost());
        }else {
            // remove the resource from the role
            resourceDataService.removeResourceRole(r.getId());

            logEvent("MODIFY", domainId, login,
                    "WEBCONSOLE", userId, "0", "ROLE", role,
                    "SUCCESS", "REMOVE RESOURCE",
                    res.getResourceId(), null,
                    "REMOVE RESOURCE FROM " + role + "-" + domain, request.getRemoteHost());
        }



        return new ModelAndView(new RedirectView(returnUrl.toString(), true));

    }

    private void logEvent(String action,String domainId, String principal,
                          String srcSystem, String userId, String targetSystem, String objectType,
                          String objectId,
                          String actionStatus, String attrName, String attrValue,
                          String requestId, String reason, String host) {


        IdmAuditLog log = new IdmAuditLog();
        log.setObjectId(objectId);
        log.setObjectTypeId(objectType);
        log.setActionId(action);
        log.setActionStatus(actionStatus);
        log.setDomainId(domainId);
        log.setUserId(userId);
        log.setPrincipal(principal);
        log.setSrcSystemId(srcSystem);
        log.setTargetSystemId(targetSystem);
        log.setCustomAttrname1(attrName);
        log.setCustomAttrvalue1(attrValue);
        log.setRequestId(requestId);
        log.setReason(reason);
        log.setHost(host);

        auditService.createLog(log);

    }

    private ModelAndView updateGroup(HttpServletRequest request, String action, String objectId, String domain, String role) {

        return null;
    }

    private ModelAndView updateMenu(HttpServletRequest request, String action, String objectId, String domain, String role) {
        return null;
    }

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

        String objectType = request.getParameter("objtype");
        String action = request.getParameter("action");
        String objectId = request.getParameter("objid");
        String role = request.getParameter("role");
        String domain = request.getParameter("domain");

        if (objectType != null && !objectType.isEmpty()) {

            if ("RES".equalsIgnoreCase(objectType)) {
                return updateResource(request, action, objectId, domain, role);

            }else if ("GROUP".equalsIgnoreCase(objectType)) {
                return updateGroup(request, action, objectId, domain, role);

            }else {
                // working with menus
                return updateMenu(request, action, objectId, domain, role);

            }

        }

        ModelAndView mav =  new ModelAndView(getSuccessView());
        mav.addObject("msg", "Error occurred. Please check the attributes passed to this controller");

        return mav;
		
	}





    private ResourceRole getResourceRole(Resource res, String roleId, String domainId) {
        ResourceRole rr = new ResourceRole();
        ResourceRoleId id = new ResourceRoleId();
        id.setDomainId(domainId);
        id.setRoleId(roleId);
        id.setResourceId(res.getResourceId());
        id.setPrivilegeId("na");
        rr.setId(id);
        return rr;
    }


	public String getSuccessView() {
		return successView;
	}


	public void setSuccessView(String successView) {
		this.successView = successView;
	}


    public ResourceDataService getResourceDataService() {
        return resourceDataService;
    }

    public void setResourceDataService(ResourceDataService resourceDataService) {
        this.resourceDataService = resourceDataService;
    }

    public AsynchIdmAuditLogWebService getAuditService() {
        return auditService;
    }

    public void setAuditService(AsynchIdmAuditLogWebService auditService) {
        this.auditService = auditService;
    }
}
