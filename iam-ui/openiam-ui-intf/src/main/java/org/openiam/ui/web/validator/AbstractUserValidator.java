package org.openiam.ui.web.validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.idm.searchbeans.OrganizationSearchBean;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.dto.OrganizationType;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.openiam.ui.web.util.OrganizationTypeStructureParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import javax.annotation.Resource;
import java.util.List;

public abstract class AbstractUserValidator {

    
    @Autowired
    private OrganizationTypeStructureParser orgTypeParser;
    
    @Resource(name = "organizationServiceClient")
    private OrganizationDataService organizationDataService;
    
    private void addMissingOrganizationError(final Errors err) {
    	err.rejectValue("organizationIds", "required",
                org.openiam.ui.util.messages.Errors.REQUIRED_USER_ORGANIZATION.getMessageName());
    }
    
    /*
	 * IDMAPPS-1282
	 * Make sure that the user has selected all possible organizations in the hierarchy presented to him.
	 */
    protected void doOrganizationChecks(final Errors err, final List<String> organizationIdList) {
    	if(CollectionUtils.isEmpty(organizationIdList)) {
    		addMissingOrganizationError(err);
    	} else {
    		final List<List<OrganizationType>> hierarchy = orgTypeParser.getOrgTypeList(null);
    		if(CollectionUtils.isNotEmpty(hierarchy)) {
    			doOrganizationChecks(err, 0, organizationIdList, hierarchy, 0, 0, null);
    		}
    	}
    }
    
    private String getOrganizationAtIdx(final List<String> organizationIdList, final int idx) {
    	try {
    		return organizationIdList.get(idx);
    	} catch(Throwable e) {
    		return null;
    	}
    }
    
    private void doOrganizationChecks(final Errors err, final int organizationIdx, final List<String> organizationIdList, List<List<OrganizationType>> hierarchy, int groupingIdx, int ladderIdx, String parentId) {
    	OrganizationType type = getOrganizationType(hierarchy, groupingIdx, ladderIdx);
    	if(type != null) {
    		final String currentOrganizationId = getOrganizationAtIdx(organizationIdList, organizationIdx);
    		List<Organization> organizationList = getOrganizations(parentId, type);
    		if(CollectionUtils.isNotEmpty(organizationList)) {
    			Organization currentOrganization = null;
    			for(final Organization organization : organizationList) {
    				if(StringUtils.equals(organization.getId(), currentOrganizationId)) {
    					currentOrganization = organization;
    					break;
    				}
    			}
    			
    			if(currentOrganization == null) {
    				addMissingOrganizationError(err);
    			} else {
    				parentId = currentOrganizationId;
    				type = getOrganizationType(hierarchy, groupingIdx, ladderIdx);
    				organizationList = getOrganizations(parentId, type);
    				final String nextOrganizationId = getOrganizationAtIdx(organizationIdList, organizationIdx + 1);
    				if(CollectionUtils.isNotEmpty(organizationList) && nextOrganizationId == null)  {
    					addMissingOrganizationError(err);
    				} else {
    					if(hierarchy.get(groupingIdx).size() - 1 == ladderIdx) { /* end of grouping.  Move to next */
        					groupingIdx += 1;
        					ladderIdx = 0;
        					parentId = null;
        				} else {
        					ladderIdx += 1;
        				}
        				doOrganizationChecks(err, organizationIdx + 1, organizationIdList, hierarchy, groupingIdx, ladderIdx, parentId);
    				}
    			}
    		}
    	}
    }
    
    private OrganizationType getOrganizationType(final List<List<OrganizationType>> hierarchy, final int groupingIdx, final int ladderIdx) {
    	try {
    		return hierarchy.get(groupingIdx).get(ladderIdx);
    	} catch(Throwable e) {
    		return null;
    	}
    }
    
    private List<Organization> getOrganizations(final String parentId, final OrganizationType type) {
    	if(type != null) {
    		final OrganizationSearchBean searchBean = new OrganizationSearchBean();
    		searchBean.setDeepCopy(false);
    		searchBean.addParentId(StringUtils.trimToNull(parentId));
    		searchBean.setOrganizationTypeId(StringUtils.trimToNull(type.getId()));
    		return organizationDataService.findBeansLocalized(searchBean, null, 0, Integer.MAX_VALUE, null);
    	} else {
    		return null;
    	}
    }
}
