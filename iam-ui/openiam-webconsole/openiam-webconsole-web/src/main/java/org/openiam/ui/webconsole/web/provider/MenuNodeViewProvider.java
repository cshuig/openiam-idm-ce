package org.openiam.ui.webconsole.web.provider;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.authmanager.util.AuthorizationConstants;
import org.openiam.idm.searchbeans.ResourceSearchBean;
import org.openiam.idm.srvc.res.dto.ResourceProp;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.web.filter.OpeniamFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class MenuNodeViewProvider {

	@Resource(name="resourceServiceClient")
	private ResourceDataService resourceDataService;
	
    @Autowired
    @Qualifier("dozerBeanMapper")
    private DozerBeanMapper mapper;
	
	public String menuTreeEntitlementsRequest(final HttpServletRequest request,
			   final String type,
			   final String id,
			   final String displayName) {
		ResourceSearchBean searchBean = new ResourceSearchBean();
		searchBean.setResourceTypeId(AuthorizationConstants.MENU_ITEM_RESOURCE_TYPE);
		searchBean.setRootsOnly(true);
		searchBean.setDeepCopy(true);
		final List<org.openiam.idm.srvc.res.dto.Resource> resultList = resourceDataService.findBeans(searchBean, 0, Integer.MAX_VALUE, OpeniamFilter.getCurrentLangauge(request));
		final List<AuthorizationMenu> menuTrees = new LinkedList<AuthorizationMenu>();
		if(CollectionUtils.isNotEmpty(resultList)) {
			for(final org.openiam.idm.srvc.res.dto.Resource resource : resultList) {
				final AuthorizationMenu menu = new AuthorizationMenu();
				menu.setId(resource.getId());
				menu.setName(resource.getName());
				if(CollectionUtils.isNotEmpty(resource.getResourceProps())) {
					menu.afterPropertiesSet(new ArrayList<ResourceProp>(resource.getResourceProps()), null);
				}
				menuTrees.add(menu);
			}
		}
		request.setAttribute("menuTrees", menuTrees);
		
		request.setAttribute("displayName", displayName);
		request.setAttribute("principalType", type);
		request.setAttribute("principalId", id);
		return "resources/menuEntitlements";
	}
}
