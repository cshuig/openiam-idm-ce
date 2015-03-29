package org.openiam.ui.rest.api.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.idm.searchbeans.AuditLogSearchBean;
import org.openiam.idm.searchbeans.MetadataElementSearchBean;
import org.openiam.idm.searchbeans.MetadataTypeSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditTarget;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.ws.LoginResponse;
import org.openiam.idm.srvc.meta.domain.MetadataTypeGrouping;
import org.openiam.idm.srvc.meta.dto.MetadataElement;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.res.dto.ResourceRisk;
import org.openiam.ui.rest.api.model.*;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class MetadataRestController extends AbstractController {
	
	
	@RequestMapping(value = "/metadata/type/groupings", method = RequestMethod.GET)
	public @ResponseBody BeanResponse getMetdataTypeGroupings(@RequestParam(required = false, value = "getAll") Boolean showAllGroupings) {
		final List<KeyNameBean> beans = new LinkedList<>();
		for(final MetadataTypeGrouping grouping : MetadataTypeGrouping.values()) {
            if((showAllGroupings!=null && showAllGroupings)
                    || grouping.isCreatable()){
                beans.add(new KeyNameBean(grouping.name(), getLocalizedMessage(String.format("openiam.ui.webconsole.meta.type.grouping.%s", grouping.name()), null)));
            }
//			if(grouping.isCreatable()) {
//				beans.add(new KeyNameBean(grouping.name(), getLocalizedMessage(String.format("openiam.ui.webconsole.meta.type.grouping.%s", grouping.name()), null)));
//			}
		}
		return new BeanResponse(beans, beans.size());
	}
	
    @RequestMapping(value = "/metadata/element/search", method = RequestMethod.GET)
    public @ResponseBody BeanResponse searchMetadata(final HttpServletRequest request,
    												 @RequestParam(required = false, value = "name") String name,
    												 @RequestParam(required = false, value = "type") String typeId,
    												 final @RequestParam(required = false, value = "excludeUIType") Boolean excludeUIType,
    												 final @RequestParam(required = true, value = "size") Integer size,
    												 final @RequestParam(required = true, value = "from") Integer from) {
		if (StringUtils.isNotBlank(name)) {
		    if (name.charAt(0) != '*') {
		    	name = "*" + name;
		    }
		    if (name.charAt(name.length() - 1) != '*') {
		    	name = name + "*";
		    }
		}
		final MetadataElementSearchBean searchBean = new MetadataElementSearchBean();
		searchBean.setAttributeName(StringUtils.trimToNull(name));
		searchBean.setDeepCopy(false);
		searchBean.addTypeId(typeId);
		if(!Boolean.FALSE.equals(excludeUIType)) {
			searchBean.addExcludedGrouping(UI_WIDGET);
		}
		final List<MetadataElement> results = metadataServiceClient.findElementBeans(searchBean, from, size, getCurrentLanguage());
		Integer count = (from.intValue() == 0) ? metadataServiceClient.countElementBeans(searchBean) : null;
		return new BeanResponse(mapper.mapToList(results, MetadataElementBean.class), count);
    }
	
	@RequestMapping(value = "/metadata/type/search", method = RequestMethod.GET)
	public @ResponseBody BeanResponse searchMetdataTypes(@RequestParam(required = false, value = "name") String name,
														 final @RequestParam(required=false, value="grouping") String grouping,
														 final @RequestParam(required=true, value="from") int from,
			   											 final @RequestParam(required=true, value="size") int size) {
		if (StringUtils.isNotBlank(name)) {
    		if (name.charAt(0) != '*') {
    			name = "*" + name;
    		}

    		if (name.charAt(name.length() - 1) != '*') {
    			name = name + "*";
    		}
    	}
		
		final MetadataTypeSearchBean searchBean = new MetadataTypeSearchBean();
		searchBean.setDeepCopy(false);
		searchBean.setName(name);
		searchBean.setGrouping(MetadataTypeGrouping.getByName(grouping));
		final List<MetadataType> results = metadataServiceClient.findTypeBeans(searchBean, from, size, getCurrentLanguage());
		final List<MetadataTypeBean> beans = mapper.mapToList(results, MetadataTypeBean.class);
		return new BeanResponse(beans, metadataServiceClient.countTypeBeans(searchBean));
	}
	
	@RequestMapping("/metadata/findLogs")
	public @ResponseBody BeanResponse findLogs(final AuditLogSearchForm auditLogSearchForm) {
		final AuditLogSearchBean searchBean = new AuditLogSearchBean();
        searchBean.setDeepCopy(false);
        if(!auditLogSearchForm.isShowChildren()) {
            searchBean.setParentOnly();
        }
        searchBean.setUserId(StringUtils.trimToNull(auditLogSearchForm.getUserId()));
        if(StringUtils.isNotBlank(auditLogSearchForm.getUserId())) {
            searchBean.setTargetId(auditLogSearchForm.getUserId());
            searchBean.setTargetType(AuditTarget.USER.value());
        }

        if(StringUtils.isNotBlank(StringUtils.trimToNull(auditLogSearchForm.getRequestorId()))) {
            searchBean.setUserId(auditLogSearchForm.getRequestorId());
        }
        if(StringUtils.isNotBlank(StringUtils.trimToNull(auditLogSearchForm.getRequestorLogin()))) {
            LoginResponse res = loginServiceClient.getLoginByManagedSys(
                    auditLogSearchForm.getRequestorLogin(), defaultManagedSysId);
            if (res.isSuccess()) {
                String requesterId = res.getPrincipal().getUserId();
                searchBean.setUserId(requesterId);
            }

        }
        if(StringUtils.isNotBlank(auditLogSearchForm.getManagedSystem())) {
            searchBean.setManagedSysId(auditLogSearchForm.getManagedSystem());
        }
        if(StringUtils.isNotBlank(auditLogSearchForm.getTargetType())) {
            searchBean.setTargetType(auditLogSearchForm.getTargetType());
        }
        if(StringUtils.isNotBlank(auditLogSearchForm.getTargetId())) {
            searchBean.setTargetId(auditLogSearchForm.getTargetId());
        }
        if(StringUtils.isNotBlank(auditLogSearchForm.getSecondaryTargetType())) {
            searchBean.setSecondaryTargetType(auditLogSearchForm.getSecondaryTargetType());
        }
        if(StringUtils.isNotBlank(auditLogSearchForm.getSecondaryTargetId())) {
            searchBean.setSecondaryTargetId(auditLogSearchForm.getSecondaryTargetId());
        }
        if(StringUtils.isNotBlank(auditLogSearchForm.getAction())) {
            searchBean.setAction(auditLogSearchForm.getAction());
        }
        if(StringUtils.isNotBlank(auditLogSearchForm.getResult())) {
            searchBean.setResult(auditLogSearchForm.getResult());
        }
        if(auditLogSearchForm.getFromDate() != null && auditLogSearchForm.getFromDate() > 0l) {
            searchBean.setFrom(new Date(auditLogSearchForm.getFromDate()));
        }
        if(auditLogSearchForm.getToDate() != null && auditLogSearchForm.getToDate() > 0l) {
            searchBean.setTo(new Date(auditLogSearchForm.getToDate()));
        }
        final int count = auditLogService.count(searchBean);
        List<AuditLogBean> auditLogBeans = new LinkedList<>();
        final List<String> fullResult = new LinkedList<>();
        final List<String> resultsTargetIds = auditLogService.getIds(searchBean, auditLogSearchForm.getFrom(), auditLogSearchForm.getSize());
        if(resultsTargetIds != null) {
            fullResult.addAll(resultsTargetIds);
        }
        if(CollectionUtils.isNotEmpty(fullResult)) {
            for(String logId : fullResult) {
                IdmAuditLog idmAuditLog = auditLogService.getLogRecord(logId);
                AuditLogBean logBean = new AuditLogBean(idmAuditLog);
                auditLogBeans.add(logBean);
            }
        }

        return new BeanResponse(auditLogBeans, (count));
	}

	@RequestMapping(value = "/metadata/groupMetadata", method = RequestMethod.GET)
	public @ResponseBody GroupSearchMetadata getGroupMetadata(final HttpServletRequest request) {
		final GroupSearchMetadata metadata = new GroupSearchMetadata();
		metadata.setManagedSystems(getManagedSystemsAsKeyNameBeans());
		return metadata;
	}
	
	@RequestMapping(value = "/metadata/organizationMetadata", method = RequestMethod.GET)
	public @ResponseBody OrganizationSearchMetadata organizationMetadata(final HttpServletRequest request) {
		final OrganizationSearchMetadata metadata = new OrganizationSearchMetadata();
		metadata.setTypes(getOrgTypeListAsKeyNameBeans());
		return metadata;
	}

    @RequestMapping(value = "/metadata/resourceMetadata", method = RequestMethod.GET)
    public @ResponseBody
    ResourceSearchMetadata getResourceMetadata(final HttpServletRequest request) {
        final ResourceSearchMetadata metadata = new ResourceSearchMetadata();
        metadata.setResourceTypeList(getResourceTypesAsKeyNameBeans(true));
        metadata.setResourceRiskList(getResourceRiskAsKeyNameBeans());
        return metadata;
    }

    @RequestMapping(value = "/metadata/roleMetadata", method = RequestMethod.GET)
    public @ResponseBody RoleSearchMetadata getRoleMetadata(final HttpServletRequest request) {
        final RoleSearchMetadata metadata = new RoleSearchMetadata();
        metadata.setManagedSystems(getManagedSystemsAsKeyNameBeans());
        return metadata;
    }
	
	@RequestMapping(value = "/metadata/resoruceTypes", method = RequestMethod.GET)
	public @ResponseBody BeanResponse resoruceTypes() {
		final List<KeyNameBean> keyNameBeans = getResourceTypesAsKeyNameBeans(true);
	    return new BeanResponse(keyNameBeans, keyNameBeans.size());
	}
	
	@RequestMapping(value="/metdata/uiTypes", method = RequestMethod.GET)
	public @ResponseBody BeanResponse geUIThemes() {
		final List<KeyNameBean> keyNameBeans = getUIThemesAsKeyNameBeans();
		return new BeanResponse(keyNameBeans, keyNameBeans.size());
	}
	
	@RequestMapping(value="/metadata/languages", method = RequestMethod.GET)
	public @ResponseBody BeanResponse getLanguages() {
		final List<LanguageBean> keyNameBeans = getUsedLanguages();
		return new BeanResponse(keyNameBeans, keyNameBeans.size());
	}

    @RequestMapping("/metadata/findActions")
    public @ResponseBody
    BeanResponse searchActions(final @RequestParam(required = false, value = "name") String name,
                               final @RequestParam(required = true, value = "size") Integer size,
                               final @RequestParam(required = true, value = "from") Integer from) {

        final AuditAction[] actions = AuditAction.values();
        List<KeyNameBean> keyNameBeanList = new ArrayList<>();
        for( AuditAction action : actions ) {
            final String actionName = action.value();
            if (name == null || actionName.contains(name.toUpperCase())) {
                keyNameBeanList.add(new KeyNameBean(action.value(), action.value()));
            }
        }
        final int count = keyNameBeanList.size();
        final int shift = (from + size > count) ? count - from : size;
        final List<KeyNameBean> resultList =
                (from < count) ? keyNameBeanList.subList(from, from + shift) : null;
        return new BeanResponse(resultList, count);
    }
}
