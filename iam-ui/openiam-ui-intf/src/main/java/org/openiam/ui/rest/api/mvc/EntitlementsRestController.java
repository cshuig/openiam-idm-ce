package org.openiam.ui.rest.api.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.authmanager.util.AuthorizationConstants;
import org.openiam.base.OrderConstants;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.searchbeans.*;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.idm.srvc.recon.ws.ReconciliationConfigResponse;
import org.openiam.idm.srvc.res.dto.ResourceRisk;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.rest.api.model.*;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/entitlements")
public class EntitlementsRestController extends AbstractController {
    @Resource(name = "userServiceClient")
    private UserDataWebService userServiceClient;
    
    @RequestMapping(value = "/hasChildren/{entity}/{entityId}", method = RequestMethod.GET)
    public @ResponseBody boolean hasChildren(@PathVariable(value="entity") String entity, 
    										 @PathVariable(value="entityId") String entityId,
    										 final HttpServletRequest request) {
    	boolean retVal = false;
    	final String requestorId = getRequesterId(request);
    	if(StringUtils.equalsIgnoreCase("group", entity)) {
    		if(groupServiceClient.getNumOfChildGroups(entityId, requestorId) > 0) {
    			retVal = true;
    		}
    		if(!retVal) {
    			retVal = (roleServiceClient.getNumOfRolesForGroup(entityId, requestorId) > 0);
    		}
    		if(!retVal) {
    			retVal = (resourceDataService.getNumOfResourceForGroup(entityId, null) > 0);
    		}
    	} else if(StringUtils.equalsIgnoreCase("role", entity)) {
    		retVal = roleServiceClient.getNumOfChildRoles(entityId, requestorId) > 0;
    		if(!retVal) {
    			retVal = resourceDataService.getNumOfResourcesForRole(entityId, null) > 0;
    		}
    	} else if(StringUtils.equalsIgnoreCase("resource", entity)) {
    		retVal = resourceDataService.getNumOfChildResources(entityId) > 0;
    	}
    	return retVal;
    }

    @RequestMapping(value = "/searchResources", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse searchResources(@RequestParam(required = false, value = "name") String name,
    							 final @RequestParam(required = false, value = "resourceTypeId") String resourceTypeId,
								 final @RequestParam(required = false, value = "userId") String userId,
    							 final @RequestParam(required = false, value = "risk") String risk,
    							 final @RequestParam(required = false, value = "returnRootsOnMenuRequest") String returnRootsOnMenuRequest,
    							 final @RequestParam(required = false, value = "attributeName") String attributeName,
    							 final @RequestParam(required = false, value = "attributeValue") String attributeValue,
    							 final @RequestParam(required = false, value = "excludeResourceType") String excludeResourceType,
    							 final @RequestParam(required = true, value = "size") Integer size,
    							 final @RequestParam(required = true, value = "from") Integer from,
                                 final @RequestParam(required = false, value = "sortBy") String sortBy,
                                 final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	boolean isMenuRootRequest = StringUtils.equalsIgnoreCase(returnRootsOnMenuRequest, Boolean.TRUE.toString())
    								&& StringUtils.equalsIgnoreCase(resourceTypeId, AuthorizationConstants.MENU_ITEM_RESOURCE_TYPE);

    	final ResourceSearchBean searchBean = new ResourceSearchBean();
    	/* ensure wildcard search */
    	if (StringUtils.isNotBlank(name)) {
    		if (name.charAt(0) != '*') {
    			name = "*" + name;
    		}

    		if (name.charAt(name.length() - 1) != '*') {
    			name = name + "*";
    		}
    	}
    	searchBean.setResourceTypeId(resourceTypeId);
    	searchBean.setName(name);
    	if (StringUtils.isNotBlank(risk)) {
    		searchBean.setRisk(ResourceRisk.valueOf(risk));
    	}
    	searchBean.setDeepCopy(false);
    	searchBean.addAttribute(attributeName, attributeValue);
    	searchBean.addExcludeResourceType(excludeResourceType);

		if (StringUtils.isNotBlank(userId)) {
			searchBean.addUserId(userId);
		}

    	if (isMenuRootRequest) {
    		searchBean.setRootsOnly(true);
    	}

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


    	final List<org.openiam.idm.srvc.res.dto.Resource> resultList = resourceDataService.findBeans(searchBean, from.intValue(), size.intValue(), getCurrentLanguage());
    	final Integer count = (from.intValue() == 0) ? resourceDataService.count(searchBean) : null;
    	return new BeanResponse(mapper.mapToList(resultList, ResourceBean.class), count);
    }

    @RequestMapping(value = "/searchGroups", method = RequestMethod.GET)
    public @ResponseBody BeanResponse searchGroups(final HttpServletRequest request,
    											   @RequestParam(required = false, value = "name") String name,
    											   final @RequestParam(required = false, value = "managedSysId") String managedSysId,
    											   final @RequestParam(required = true, value = "size") int size,
    											   final @RequestParam(required = true, value = "from") int from,
    											   final @RequestParam(required = false, value = "attributeName") String attributeName,
    											   final @RequestParam(required = false, value = "attributeValue") String attributeValue,
                                                   final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                   final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterIdFromCookie(request);

    	if (StringUtils.isNotBlank(name)) {
    		if (name.charAt(0) != '*') {
    			name = "*" + name;
    		}

    		if (name.charAt(name.length() - 1) != '*') {
    			name = name + "*";
    		}
    	}
    	final GroupSearchBean searchBean = new GroupSearchBean();
    	searchBean.setManagedSysId(managedSysId);
    	searchBean.setName(name);
    	searchBean.setDeepCopy(false);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }

    	searchBean.addAttribute(attributeName, attributeValue);

    	final List<Group> results = groupServiceClient.findBeans(searchBean, requesterId, from, size);
    	final Integer count = (from == 0) ? groupServiceClient.countBeans(searchBean, requesterId) : null;

    	return new BeanResponse(mapper.mapToList(results, GroupBean.class), count);
    }

    @RequestMapping(value = "/searchRoles", method = RequestMethod.GET)
    public @ResponseBody BeanResponse searchRoles(final HttpServletRequest request,
    											  @RequestParam(required = false, value = "name") String name,
    											  final @RequestParam(required = false, value = "managedSysId") String managedSysId,
    											  final @RequestParam(required = true, value = "size") int size,
    											  final @RequestParam(required = true, value = "from") int from,
                                                  final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                  final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	
    	String requesterId = getRequesterIdFromCookie(request);

    	if (StringUtils.isNotBlank(name)) {
    		if (name.charAt(0) != '*') {
    			name = "*" + name;
    		}
    		
    		if (name.charAt(name.length() - 1) != '*') {
    			name = name + "*";
    		}
    	}
	
    	final RoleSearchBean searchBean = new RoleSearchBean();
    	searchBean.setName(StringUtils.trimToNull(name));
    	searchBean.setManagedSysId(StringUtils.trimToNull(managedSysId));
    	searchBean.setDeepCopy(false);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }

    	
    	final List<Role> results = roleServiceClient.findBeans(searchBean, requesterId, from, size);
    	final Integer count = (from == 0) ? roleServiceClient.countBeans(searchBean, requesterId) : null;
    	return new BeanResponse(mapper.mapToList(results, RoleBean.class), count);
    }

    @RequestMapping(value = "/searchResourceTypes", method = RequestMethod.GET)
    public @ResponseBody BeanResponse searchResourceTypes(@RequestParam(required = false, value = "decs") String description,
    													  final @RequestParam(required = false, value = "provisionResource") Integer provRes,
    													  final @RequestParam(required = false, value = "processName") String processName,
    													  final @RequestParam(required = true, value = "size") Integer size,
    													  final @RequestParam(required = true, value = "from") Integer from) {
    	final ResourceTypeSearchBean searchBean = new ResourceTypeSearchBean();
    	/* ensure wildcard search */
    	if (StringUtils.isNotBlank(description)) {
    		if (description.charAt(0) != '*') {
    			description = "*" + description;
    		}
    		
    		if (description.charAt(description.length() - 1) != '*') {
    			description = description + "*";
    		}
    	}
    	searchBean.setDescription(description);
    	searchBean.setProcessName(processName);
    	searchBean.setProvisionResource(provRes);
    	
    	final List<org.openiam.idm.srvc.res.dto.ResourceType> resultList = resourceDataService.findResourceTypes(searchBean, from.intValue(), size.intValue(), getCurrentLanguage());
    	final Integer count = (from.intValue() == 0) ? resourceDataService.countResourceTypes(searchBean) : null;
    	return new BeanResponse(mapper.mapToList(resultList, ResourceTypeBean.class), count);
    }


    @RequestMapping(value = "/searchReconciliations", method = RequestMethod.GET)
    public @ResponseBody BeanResponse searchReconciliations(final HttpServletRequest request,
                                                  @RequestParam(required = false, value = "reconciliationType") String reconciliationType,
                                                  final @RequestParam(required = false, value = "managedSysId") String managedSysId,
                                                  final @RequestParam(required = true, value = "size") Integer size,
                                                  final @RequestParam(required = true, value = "from") Integer from) {
        if (StringUtils.isEmpty(managedSysId)) {
            BeanResponse response = new BeanResponse(Collections.EMPTY_LIST, 0);
            response.setError("Required property 'managedSysId' can't be empty.");
            return response;
        }

        ReconConfigSearchBean searchBean = new ReconConfigSearchBean();
        if(StringUtils.isNotEmpty(reconciliationType)) {
            searchBean.setReconType(reconciliationType);
        }
        searchBean.setManagedSysId(managedSysId);
        ReconciliationConfigResponse response = reconciliationWebService.findReconConfig(searchBean, from.intValue(), size.intValue(), getCurrentLanguage());
        List<ReconciliationConfig> configList = (List<ReconciliationConfig>) response.getConfigList();
        final Integer count = (from.intValue() == 0) ? reconciliationWebService.countReconConfig(searchBean) : null;
        return new BeanResponse(mapper.mapToList(configList, ReconcileConfigBean.class), count);
    }

    @RequestMapping(value = "/searchOrganizations", method = RequestMethod.GET)
    public @ResponseBody BeanResponse searchOrganizations(final HttpServletRequest request,
    													  @RequestParam(required = false, value = "name") String name,
    													  @RequestParam(required = false, value = "childId") String childId,
    													  @RequestParam(required = false, value = "parentId") String parentId,
    													  @RequestParam(required = false, value = "validParentTypeId") String validParentTypeId,
    													  @RequestParam(required = false, value = "organizationTypeId[]") String[] organizationTypeIdList,
                                                          @RequestParam(required = false, value = "isSelectable") Boolean isSelectable,
    													  final @RequestParam(required = true, value = "size") Integer size,
    													  final @RequestParam(required = true, value = "from") Integer from,
                                                          final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                          final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = this.getRequesterIdFromCookie(request);

    	if (StringUtils.isNotBlank(name)) {
    		if (name.charAt(0) != '*') {
    			name = "*" + name;
    		}
    		if (name.charAt(name.length() - 1) != '*') {
    			name = name + "*";
    		}
    	}
        Set<String> orgTypeIds=new HashSet<>();
        if(organizationTypeIdList!=null){
            for (String type:organizationTypeIdList){
                String t = StringUtils.trimToNull(type);
                if(StringUtils.isNotBlank(t))
                    orgTypeIds.add(t);
            }
        }

    	final OrganizationSearchBean searchBean = new OrganizationSearchBean();
    	searchBean.setName(StringUtils.trimToNull(name));
    	searchBean.setDeepCopy(false);
    	searchBean.addChildId(StringUtils.trimToNull(childId));
    	searchBean.addParentId(StringUtils.trimToNull(parentId));
    	searchBean.setOrganizationTypeIdSet(orgTypeIds);
    	searchBean.setValidParentTypeId(StringUtils.trimToNull(validParentTypeId));

        if(isSelectable!=null && isSelectable){
            searchBean.setIsSelectable(isSelectable);
        }

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }
    	
    	final List<Organization> results = organizationDataService.findBeansLocalized(searchBean, requesterId, from, size, getCurrentLanguage());
    	Integer count = (from.intValue() == 0) ? organizationDataService.count(searchBean, requesterId) : null;

    	return new BeanResponse(mapper.mapToList(results, OrganizationBean.class), count);
    }

    @RequestMapping(value = "/getResourcesForUser", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getResourcesForUser(final @RequestParam(required = true, value = "id") String userId,
    													  final @RequestParam(required = true, value = "size") Integer size,
    													  final @RequestParam(required = true, value = "from") Integer from,
    													  final @RequestParam(required = false, value = "ignoreMenus") Boolean ignoreMenus,
                                                          final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                          final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	final ResourceSearchBean searchBean = getSearchBean(ignoreMenus);
        searchBean.addUserId(userId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }

    	final Integer count = (from.intValue() == 0) ? resourceDataService.count(searchBean) : null;
    	final List<org.openiam.idm.srvc.res.dto.Resource> results = resourceDataService.findBeans(searchBean, from,
                                                                                                  size,
                                                                                                  getCurrentLanguage());
    	return new BeanResponse(mapper.mapToList(results, ResourceBean.class), count);
    }

    @RequestMapping(value = "/getGroupsForUser", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getGroupsForUser(final HttpServletRequest request,
    												   final @RequestParam(required = true, value = "id") String userId,
    												   final @RequestParam(required = true, value = "size") Integer size,
    												   final @RequestParam(required = true, value = "from") Integer from,
    												   final @RequestParam(required = false, value = "deepFlag", defaultValue = "false") Boolean deepFlag,
                                                       final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                       final @RequestParam(required = false, value = "orderBy") String orderBy) {
		String requesterId = getRequesterId(request);

        final GroupSearchBean searchBean = new GroupSearchBean();
        searchBean.addUserId(userId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }

        final Integer count = (from.intValue() == 0) ? groupServiceClient.countBeans(searchBean, requesterId) : null;
        final List<Group> results = groupServiceClient.findBeansLocalize(searchBean, requesterId, from, size,
                                                                         getCurrentLanguage());

//		final Integer count = (from.intValue() == 0) ? groupServiceClient.getNumOfGroupsForUser(userId, requesterId) : null;
//		final List<Group> results = groupServiceClient.getGroupsForUser(userId, requesterId, (deepFlag != null) ? deepFlag : false, from, size);

		final List<GroupBean> resList = new ArrayList<GroupBean>();
		if (CollectionUtils.isNotEmpty(results)) {
			for (Group group : results) {
				final GroupBean bean = new GroupBean();
				bean.setId(group.getId());
				bean.setName(group.getName());
				bean.setManagedSysId(group.getManagedSysId());
				bean.setManagedSysName(group.getManagedSysName());
				bean.setHasChild(CollectionUtils.isNotEmpty(group.getChildGroups())
						|| CollectionUtils.isNotEmpty(group.getRoles())
						|| CollectionUtils.isNotEmpty(group.getResources()));
				resList.add(bean);
			}
		}

		return new BeanResponse(resList, count);
    }

    @RequestMapping(value = "/getRolesForUser", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getRolesForUser(final HttpServletRequest request,
    							 final @RequestParam(required = true, value = "id") String userId,
    							 final @RequestParam(required = true, value = "size") Integer size,
    							 final @RequestParam(required = true, value = "from") Integer from,
    							 final @RequestParam(required = false, value = "deepFlag", defaultValue = "false") Boolean deepFlag,
                                 final @RequestParam(required = false, value = "sortBy") String sortBy,
                                 final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterId(request);

        final RoleSearchBean searchBean = new RoleSearchBean();
        searchBean.addUserId(userId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


        final Integer count = (from.intValue() == 0) ? roleServiceClient.countBeans(searchBean, requesterId) : null;
        final List<Role> results = roleServiceClient.findBeans(searchBean, requesterId, from, size);

//    	final Integer count = (from.intValue() == 0) ? roleServiceClient.getNumOfRolesForUser(userId, requesterId) : null;
//    	final List<Role> results = roleServiceClient.getRolesForUser(userId, requesterId, (deepFlag != null) ? deepFlag : false, from, size);

    	final List<RoleBean> resList = new ArrayList<RoleBean>();
    	if (CollectionUtils.isNotEmpty(results)) {
    		for (final Role role : results) {
    			final RoleBean bean = new RoleBean();
    			bean.setId(role.getId());
    			bean.setName(role.getName());
    			bean.setManagedSysId(role.getManagedSysId());
    			bean.setManagedSysName(role.getManagedSysName());
    			bean.setHasChild(CollectionUtils.isNotEmpty(role.getChildRoles())
    					|| CollectionUtils.isNotEmpty(role.getGroups())
    					|| CollectionUtils.isNotEmpty(role.getResources()));
    			resList.add(bean);
    		}
    	}
    	return new BeanResponse(resList, count);
    }

    @RequestMapping(value = "/getOrganizationsForUser", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getOrganizationsForUser(final HttpServletRequest request,
    														  final @RequestParam(required = true, value = "id") String userId,
    														  final @RequestParam(required = true, value = "size") Integer size,
    														  final @RequestParam(required = true, value = "from") Integer from,
                                                              final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                              final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	final String requesterId = getRequesterId(request);

        final OrganizationSearchBean searchBean = new OrganizationSearchBean();
        searchBean.addUserId(userId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }

    	final Integer count = (from.intValue() == 0) ? organizationDataService.count(searchBean, requesterId) : null;
    	final List<Organization> results = organizationDataService.findBeansLocalized(searchBean, requesterId, from,
                                                                                      size, getCurrentLanguage());
    	return new BeanResponse(mapper.mapToList(results, OrganizationBean.class), count);
    }

    @RequestMapping(value = "/getGroupsForResource", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getGroupsForResource(final HttpServletRequest request,
    													   final @RequestParam(required = true, value = "id") String resourceId,
    													   final @RequestParam(required = true, value = "size") Integer size,
    													   final @RequestParam(required = true, value = "from") Integer from,
                                                           final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                           final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterId(request);

        final GroupSearchBean searchBean = new GroupSearchBean();
        searchBean.addResourceId(resourceId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }
    	
    	final Integer count = (from.intValue() == 0) ? groupServiceClient.countBeans(searchBean, requesterId) : null;
    	final List<Group> results = groupServiceClient.findBeansLocalize(searchBean, requesterId, from, size,
                                                                         getCurrentLanguage());
    	return new BeanResponse(mapper.mapToList(results, GroupBean.class), count);
    }

    @RequestMapping(value = "/getRolesForResource", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getRolesForResource(final HttpServletRequest request,
    													  final @RequestParam(required = true, value = "id") String resourceId,
    													  final @RequestParam(required = true, value = "size") Integer size,
    													  final @RequestParam(required = true, value = "from") Integer from,
                                                          final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                          final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterId(request);

        final RoleSearchBean searchBean = new RoleSearchBean();
        searchBean.addResourceId(resourceId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


    	final Integer count = (from.intValue() == 0) ? roleServiceClient.countBeans(searchBean, requesterId) : null;
    	final List<Role> results = roleServiceClient.findBeans(searchBean, requesterId, from, size);
    	return new BeanResponse(mapper.mapToList(results, RoleBean.class), count);
    }

    @RequestMapping(value = "/getUsersForResource", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getUsersForResource(final HttpServletRequest request,
    													  final @RequestParam(required = true, value = "id") String resourceId,
    													  final @RequestParam(required = true, value = "size") Integer size,
    													  final @RequestParam(required = true, value = "from") Integer from,
                                                          final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                          final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterId(request);
        UserSearchBean searchBean = new UserSearchBean();
        searchBean.setRequesterId(requesterId);
        searchBean.addResourceId(resourceId);


        if(StringUtils.isNotEmpty(sortBy)) {
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add(new SortParam((orderBy==null)?OrderConstants.ASC:OrderConstants.valueOf(orderBy), sortBy));
            searchBean.setSortBy(sortParamList);
        }
        searchBean.setDeepCopy(false);

        final List<User> userList = userServiceClient.getUsersForResourceWithSorting(searchBean, from, size);
        final Integer count = (from == 0) ? userServiceClient.getNumOfUsersForResource(resourceId, requesterId) : null;

    	final List<UserBean> beanList = new LinkedList<UserBean>();
    	if (CollectionUtils.isNotEmpty(userList)) {
    		for (final User user : userList) {
    			beanList.add(UserBean.getInstance(user));
    		}
    	}
    	return new BeanResponse(beanList, count);
    }

    @RequestMapping(value = "/getChildResources", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getChildResources(final @RequestParam(required = true, value = "id") String resourceId,
    													final @RequestParam(required = true, value = "size") Integer size,
    													final @RequestParam(required = true, value = "from") Integer from,
    													final @RequestParam(required = false, value = "deepFlag", defaultValue = "false") Boolean deepFlag,
                                                        final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                        final @RequestParam(required = false, value = "orderBy") String orderBy) {

        final ResourceSearchBean searchBean = getSearchBean(false);
        searchBean.setDeepCopy(deepFlag);
        searchBean.addParentId(resourceId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


    	final Integer count = (from.intValue() == 0) ? resourceDataService.count(searchBean) : null;
    	final List<org.openiam.idm.srvc.res.dto.Resource> results = resourceDataService.findBeans(searchBean, from,
                                                                                                  size,
                                                                                                  getCurrentLanguage());
    	
    	List<ResourceBean> resList = new ArrayList<ResourceBean>();
    	if (CollectionUtils.isNotEmpty(results)) {
    		for (org.openiam.idm.srvc.res.dto.Resource res : results) {
    			final ResourceBean bean = mapper.mapToObject(res, ResourceBean.class);
    			bean.setHasChild(CollectionUtils.isNotEmpty(res.getChildResources())
    					|| CollectionUtils.isNotEmpty(res.getGroups()) || CollectionUtils.isNotEmpty(res.getRoles()));

    			resList.add(bean);
    		}
    	}
    	return new BeanResponse(resList, count);
    }

    @RequestMapping(value = "/getParentResources", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getParentResources(final @RequestParam(required = true, value = "id") String resourceId,
    													 final @RequestParam(required = true, value = "size") Integer size,
    													 final @RequestParam(required = true, value = "from") Integer from,
                                                         final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                         final @RequestParam(required = false, value = "orderBy") String orderBy) {

        final ResourceSearchBean searchBean = getSearchBean(false);
        searchBean.addChildId(resourceId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


    	final Integer count = (from.intValue() == 0) ? resourceDataService.count(searchBean) : null;
    	final List<org.openiam.idm.srvc.res.dto.Resource> results = resourceDataService.findBeans(searchBean, from,
                                                                                                  size,
                                                                                                  getCurrentLanguage());
    	return new BeanResponse(mapper.mapToList(results, ResourceBean.class), count);
    }

    @RequestMapping(value = "/getResourcesForRole", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getResourcesForRole(final @RequestParam(required = true, value = "id") String roleId,
    													  final @RequestParam(required = true, value = "size") Integer size,
    													  final @RequestParam(required = true, value = "from") Integer from,
    													  final @RequestParam(required = false, value = "ignoreMenus") Boolean ignoreMenus,
                                                          final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                          final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	final ResourceSearchBean searchBean = getSearchBean(ignoreMenus);
        searchBean.addRoleId(roleId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }

    	final Integer count = (from.intValue() == 0) ? resourceDataService.count(searchBean) : null;
    	final List<org.openiam.idm.srvc.res.dto.Resource> results = resourceDataService.findBeans(searchBean, from,
                                                                                                  size,
                                                                                                  getCurrentLanguage());
    	return new BeanResponse(mapper.mapToList(results, ResourceBean.class), count);
    }

    @RequestMapping(value = "/getGroupsForRole", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getGroupsForRole(final HttpServletRequest request,
    												   final @RequestParam(required = true, value = "id") String roleId,
    												   final @RequestParam(required = true, value = "size") Integer size,
    												   final @RequestParam(required = true, value = "from") Integer from,
                                                       final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                       final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	
    	String requesterId = getRequesterId(request);

        final GroupSearchBean searchBean = new GroupSearchBean();
        searchBean.addRoleId(roleId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


    	final Integer count = groupServiceClient.countBeans(searchBean, requesterId);
    	final List<Group> results = groupServiceClient.findBeansLocalize(searchBean, requesterId, from, size,
                                                                         getCurrentLanguage());

    	return new BeanResponse(mapper.mapToList(results, GroupBean.class), count);
    }

    @RequestMapping(value = "/getUsersForRole", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getUsersForRole(final HttpServletRequest request,
    												  final @RequestParam(required = true, value = "id") String roleId,
    												  final @RequestParam(required = true, value = "size") Integer size,
    												  final @RequestParam(required = true, value = "from") Integer from,
                                                      final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                      final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterId(request);

        UserSearchBean searchBean = new UserSearchBean();
        searchBean.setRequesterId(requesterId);
        searchBean.addRoleId(roleId);

        if(StringUtils.isNotEmpty(sortBy)) {
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add(new SortParam((orderBy==null)?OrderConstants.ASC:OrderConstants.valueOf(orderBy), sortBy));
            searchBean.setSortBy(sortParamList);
        }
        searchBean.setDeepCopy(false);

        final List<User> userList = userServiceClient.findBeans(searchBean, from, size);
        final Integer count = userServiceClient.count(searchBean);


//    	final List<User> userList = userServiceClient.getUsersForRole(roleId, requesterId, from, size);
//    	final Integer count = (from == 0) ? userServiceClient.getNumOfUsersForRole(roleId, requesterId) : null;
    	final List<UserBean> beanList = new LinkedList<UserBean>();
    	if (CollectionUtils.isNotEmpty(userList)) {
    		for (final User user : userList) {
    			beanList.add(UserBean.getInstance(user));
    		}
    	}
    	return new BeanResponse(beanList, count);
    }

    @RequestMapping(value = "/getChildRoles", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getChildRoles(final HttpServletRequest request,
    												final @RequestParam(required = true, value = "id") String roleId,
    												final @RequestParam(required = true, value = "size") Integer size,
    												final @RequestParam(required = true, value = "from") Integer from,
    												final @RequestParam(required = false, value = "deepFlag", defaultValue = "false") Boolean deepFlag,
                                                    final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                    final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterId(request);

        final RoleSearchBean searchBean = new RoleSearchBean();
        searchBean.addParentId(roleId);
        searchBean.setDeepCopy(deepFlag);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }

    	final Integer count = roleServiceClient.countBeans(searchBean, requesterId);
    	final List<Role> results = roleServiceClient.findBeans(searchBean, requesterId, from, size);

    	final List<RoleBean> resList = new ArrayList<RoleBean>();
    	if (CollectionUtils.isNotEmpty(results)) {
    		for (final Role role : results) {
    			final RoleBean bean = new RoleBean();
    			bean.setId(role.getId());
    			bean.setName(role.getName());
    			bean.setManagedSysId(role.getManagedSysId());
    			bean.setManagedSysName(role.getManagedSysName());
    			bean.setHasChild(CollectionUtils.isNotEmpty(role.getChildRoles())
    					|| CollectionUtils.isNotEmpty(role.getGroups())
    					|| CollectionUtils.isNotEmpty(role.getResources()));
    			resList.add(bean);
    		}
    	}
    	return new BeanResponse(resList, count);
    }

    @RequestMapping(value = "/getParentRoles", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getParentRoles(final HttpServletRequest request,
    												 final @RequestParam(required = true, value = "id") String roleId,
    												 final @RequestParam(required = true, value = "size") Integer size,
    												 final @RequestParam(required = true, value = "from") Integer from,
                                                     final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                     final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterId(request);

        final RoleSearchBean searchBean = new RoleSearchBean();
        searchBean.addChildId(roleId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


    	final Integer count = roleServiceClient.countBeans(searchBean, requesterId);
    	final List<Role> results = roleServiceClient.findBeans(searchBean, requesterId, from, size);
    	return new BeanResponse(mapper.mapToList(results, RoleBean.class), count);
    }

    @RequestMapping(value = "/getRolesForGroup", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getRolesForGroup(final HttpServletRequest request,
    												   final @RequestParam(required = true, value = "id") String groupId,
    												   final @RequestParam(required = true, value = "size") Integer size,
    												   final @RequestParam(required = true, value = "from") Integer from,
                                                       final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                       final @RequestParam(required = false, value = "orderBy") String orderBy) {

    	String requesterId = getRequesterId(request);

        final RoleSearchBean searchBean = new RoleSearchBean();
        searchBean.addGroupId(groupId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


    	final Integer count = roleServiceClient.countBeans(searchBean, requesterId);
    	final List<Role> results = roleServiceClient.findBeans(searchBean, requesterId, from, size);
    	return new BeanResponse(mapper.mapToList(results, RoleBean.class), count);
    }

    @RequestMapping(value = "/getResourcesForGroup", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getResourcesForGroup(final @RequestParam(required = true, value = "id") String groupId,
    													   final @RequestParam(required = true, value = "size") Integer size,
    													   final @RequestParam(required = true, value = "from") Integer from,
    													   final @RequestParam(required = false, value = "ignoreMenus") Boolean ignoreMenus,
                                                           final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                           final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	final ResourceSearchBean searchBean = getSearchBean(ignoreMenus);
        searchBean.addGroupId(groupId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


    	final Integer count = resourceDataService.count(searchBean);
    	final List<org.openiam.idm.srvc.res.dto.Resource> results = resourceDataService.findBeans(searchBean, from,
                                                                                                  size,
                                                                                                  getCurrentLanguage());

    	return new BeanResponse(mapper.mapToList(results, ResourceBean.class), count);
    }

    private ResourceSearchBean getSearchBean(final Boolean ignoreMenus) {
    	final ResourceSearchBean searchBean = new ResourceSearchBean();
    	if (Boolean.TRUE.equals(ignoreMenus)) {
    		searchBean.addExcludeResourceType(AuthorizationConstants.MENU_ITEM_RESOURCE_TYPE);
    	}
    	return searchBean;
    }

    @RequestMapping(value = "/getUsersForGroup", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getUsersForGroup(final HttpServletRequest request,
    												   final @RequestParam(required = true, value = "id") String groupId,
    												   final @RequestParam(required = true, value = "size") Integer size,
    												   final @RequestParam(required = true, value = "from") Integer from,
                                                       final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                       final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterId(request);

        UserSearchBean searchBean = new UserSearchBean();
        searchBean.setRequesterId(requesterId);
        searchBean.addGroupId(groupId);

        if(StringUtils.isNotEmpty(sortBy)) {
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add(new SortParam((orderBy==null)?OrderConstants.ASC:OrderConstants.valueOf(orderBy), sortBy));
            searchBean.setSortBy(sortParamList);
        }
        searchBean.setDeepCopy(false);

    	final List<User> userList = userServiceClient.findBeans(searchBean, from, size);
    	final Integer count = userServiceClient.count(searchBean);
    	final List<UserBean> beanList = new LinkedList<UserBean>();
    	if (CollectionUtils.isNotEmpty(userList)) {
    		for (final User user : userList) {
    			beanList.add(UserBean.getInstance(user));
    		}
    	}
    	return new BeanResponse(beanList, count);
    }

    @RequestMapping(value = "/getChildGroups", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getChildGroups(final HttpServletRequest request,
    												 final @RequestParam(required = true, value = "id") String groupId,
    												 final @RequestParam(required = true, value = "size") Integer size,
    												 final @RequestParam(required = true, value = "from") Integer from,
    												 final @RequestParam(required = false, value = "deepFlag", defaultValue = "false") Boolean deepFlag,
                                                     final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                     final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterId(request);

        final GroupSearchBean searchBean = new GroupSearchBean();
        searchBean.addParentId(groupId);
        searchBean.setDeepCopy(deepFlag);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


        final List<Group> results = groupServiceClient.findBeans(searchBean, requesterId, from, size);
        final Integer count =  groupServiceClient.countBeans(searchBean, requesterId);

//    	final Integer count = (from.intValue() == 0) ? groupServiceClient.getNumOfChildGroups(groupId, requesterId) : null;
//    	final List<Group> results = groupServiceClient.getChildGroups(groupId, requesterId, deepFlag, from, size);

    	final List<GroupBean> resList = new ArrayList<GroupBean>();
    	if (CollectionUtils.isNotEmpty(results)) {
    		for (final Group group : results) {
    			final GroupBean bean = new GroupBean();
    			bean.setId(group.getId());
    			bean.setName(group.getName());
    			bean.setManagedSysId(group.getManagedSysId());
    			bean.setManagedSysName(group.getManagedSysName());
    			bean.setHasChild(CollectionUtils.isNotEmpty(group.getChildGroups())
    					|| CollectionUtils.isNotEmpty(group.getRoles())
    					|| CollectionUtils.isNotEmpty(group.getResources()));
    			resList.add(bean);
    		}
    	}

    	return new BeanResponse(resList, count);
    }
    
    @RequestMapping(value = "/getParentOrganizations", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getParentOrganizations(final HttpServletRequest request, final @RequestParam(required = true, value = "id") String organizationId,
                                        final @RequestParam(required = true, value = "size") Integer size,
                                        final @RequestParam(required = true, value = "from") Integer from,
                                        final @RequestParam(required = false, value = "sortBy") String sortBy,
                                        final @RequestParam(required = false, value = "orderBy") String orderBy) {

        String requesterId = this.getRequesterId(request);

        final OrganizationSearchBean searchBean = new OrganizationSearchBean();
        searchBean.addChildId(organizationId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }

        final Integer count = (from.intValue() == 0) ? organizationDataService.count(searchBean, requesterId) : null;
        final List<Organization> results = organizationDataService.findBeansLocalized(searchBean, requesterId, from, size, getCurrentLanguage());
        return new BeanResponse(mapper.mapToList(results, OrganizationBean.class), count);
    }

    @RequestMapping(value = "/getParentGroups", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getParentGroups(final HttpServletRequest request,
    												  final @RequestParam(required = true, value = "id") String groupId,
    												  final @RequestParam(required = true, value = "size") Integer size,
    												  final @RequestParam(required = true, value = "from") Integer from,
                                                      final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                      final @RequestParam(required = false, value = "orderBy") String orderBy) {
    	String requesterId = getRequesterId(request);


        final GroupSearchBean searchBean = new GroupSearchBean();
        searchBean.addChildId(groupId);
        searchBean.setDeepCopy(false);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }


        final List<Group> results = groupServiceClient.findBeans(searchBean, requesterId, from, size);
        final Integer count =  groupServiceClient.countBeans(searchBean, requesterId);

//    	final Integer count = (from.intValue() == 0) ? groupServiceClient.getNumOfParentGroups(groupId, requesterId) : null;
//    	final List<Group> results = groupServiceClient.getParentGroups(groupId, requesterId, from, size);

    	return new BeanResponse(mapper.mapToList(results, GroupBean.class), count);
    }
}
