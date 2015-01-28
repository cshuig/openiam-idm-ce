package org.openiam.ui.web.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.idm.searchbeans.OrganizationTypeSearchBean;
import org.openiam.idm.srvc.org.dto.OrganizationType;
import org.openiam.idm.srvc.org.service.OrganizationTypeDataService;
import org.openiam.ui.web.filter.OpeniamFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class OrganizationTypeStructureParser implements InitializingBean {
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="organizationTypeClient")
	private OrganizationTypeDataService organizationTypeClient;
	
	@Value("${org.openiam.selfservice.organization.membership.user.screen.hierarchy}")
	private String orgHierarchy;
	
	private List<String> allOrgIds = new LinkedList<>();
	private List<List<String>> orgTypeList = new LinkedList<>();
	
	public List<List<OrganizationType>> getOrgTypeList(final HttpServletRequest request) {
		final List<List<OrganizationType>> retVal = new LinkedList<>();
    	List<OrganizationType> organizationTypes = null;
    	if(CollectionUtils.isNotEmpty(allOrgIds)) {
    		final OrganizationTypeSearchBean searchBean = new OrganizationTypeSearchBean();
    		searchBean.setKeySet(new HashSet<String>(allOrgIds));
    		searchBean.setDeepCopy(false);
    		organizationTypes =  organizationTypeClient.findBeans(searchBean, 0, Integer.MAX_VALUE, OpeniamFilter.getCurrentLangauge(request));
    		organizationTypes = (organizationTypes == null) ? new LinkedList<OrganizationType>() : organizationTypes;
    		final Map<String, OrganizationType> typeMap = new HashMap<String, OrganizationType>();
    		for(final OrganizationType type : organizationTypes) {
    			typeMap.put(type.getId(), type);
    		}
    		for(final List<String> grouping : orgTypeList) {
    			final List<OrganizationType> typeList = new LinkedList<>();
    			for(final String orgId : grouping) {
    				final OrganizationType type = typeMap.get(orgId);
    				if(type != null) {
    					typeList.add(type);
    				}
    			}
    			retVal.add(typeList);
    		}
    	}
    	return retVal;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(orgHierarchy.contains(",")) {
			final String propertyWarning = String.format("property 'org.openiam.selfservice.organization.membership.user.screen.hierarchy' contains a comma.  " +
								   						 "This format was deprecated in 3.1.4+.  See the OpenIAM wiki for details..");
			throw new Exception(propertyWarning);
		}
		final String[] groupings = StringUtils.split(orgHierarchy, "|");
		for(int i = 0; i < groupings.length; i++) {
			final String[] ladder = StringUtils.split(groupings[i], "->");
			final List<String> ladderList = new LinkedList<>();
			for(final String orgId : ladder) {
				allOrgIds.add(orgId);
				ladderList.add(orgId);
			}
			orgTypeList.add(ladderList);
		}
	}
}
