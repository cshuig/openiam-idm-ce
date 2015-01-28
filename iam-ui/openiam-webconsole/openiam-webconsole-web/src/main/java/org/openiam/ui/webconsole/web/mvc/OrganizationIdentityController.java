package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.idm.srvc.auth.dto.IdentityDto;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.ui.rest.api.model.IdentityBean;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class OrganizationIdentityController extends AbstractController {

    @Value("${org.openiam.ui.organization.edit.menu.id}")
    private String organizationEditMenuName;

    @RequestMapping(value="/editOrgIdentity", method = RequestMethod.GET)
    public String getEditPage(final HttpServletRequest request,
                              final HttpServletResponse response,
                              final @RequestParam(required=true, value="id") String orgId) throws IOException {
        final Organization org = organizationDataService.getOrganizationLocalized(orgId,
                getRequesterId(request), getCurrentLanguage());
        if(org == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        List<IdentityDto> identityDtoList = identityWebService.getIdentities(orgId);
        final List<KeyNameBean> managedSysList = getManagedSysList();

        request.setAttribute("managedSystems", (managedSysList != null) ? jacksonMapper.writeValueAsString(managedSysList) : null);
        request.setAttribute("identities", identityDtoList);
        request.setAttribute("org", org);
        request.setAttribute("dateFormatDP", DateFormatStr.getDpDate());

        setMenuTree(request, organizationEditMenuName);
        return "jar:organization/identityOrg";
    }

    @RequestMapping(value="/getOrgIdentities", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getOrgIdentities(final @RequestParam(required=true, value="id") String orgId) {

        final List<IdentityDto> identityList = identityWebService.getIdentities(orgId);
        final List<IdentityBean> identityBeans = new LinkedList<IdentityBean>();
        if(CollectionUtils.isNotEmpty(identityList)) {
            final Map<String, KeyNameBean> managedSysMap = getManagedSysMap();
            for(final IdentityDto identity : identityList) {
                final IdentityBean identityBean = new IdentityBean();
                identityBean.setIdentity(identity.getIdentity());
                identityBean.setCreateDate(identity.getCreateDate());
                identityBean.setCreatedBy(identity.getCreatedBy());
                identityBean.setReferredObjectId(identity.getReferredObjectId());
                identityBean.setType(identity.getType());
                identityBean.setStatus(identity.getStatus());
                if(identity.getManagedSysId() != null) {
                    final KeyNameBean mSys = managedSysMap.get(identity.getManagedSysId());
                    if(mSys != null) {
                        identityBean.setManagedSys(mSys.getName());
                        identityBean.setManagedSysId(mSys.getId());
                    }
                }
                identityBeans.add(identityBean);
            }
        }
        return new BeanResponse(identityBeans, identityBeans.size());
    }

    private Map<String, KeyNameBean> getManagedSysMap() {
        final Map<String, KeyNameBean> map = new HashMap<String, KeyNameBean>();
        final List<KeyNameBean> list = getManagedSysList();
        if(CollectionUtils.isNotEmpty(list)) {
            for(final KeyNameBean key : list) {
                map.put(key.getId(), key);
            }
        }
        return map;
    }

    private List<KeyNameBean> getManagedSysList() {
        final List<ManagedSysDto> managedSystems = managedSysServiceClient.getAllManagedSys();
        final List<KeyNameBean> keyNameBeanList = new LinkedList<KeyNameBean>();
        if(managedSystems != null) {
            for(final ManagedSysDto dto : managedSystems) {
                if(dto != null) {
                    keyNameBeanList.add(mapper.mapToObject(dto, KeyNameBean.class));
                }
            }
        }
        return keyNameBeanList;
    }

}
