package org.openiam.ui.webconsole.web.mvc;

import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.role.ws.RoleDataWebService;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.provision.service.AsynchUserProvisionService;
import org.openiam.provision.service.ProvisionService;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseUserController extends AbstractWebconsoleController {

    @Value("${org.openiam.ui.landingpage.user.root.id}")
    protected String userRootMenuId;
    @Value("${org.openiam.ui.landingpage.user.edit.root.id}")
    protected String userEditRootMenuId;

    @Value("${org.openiam.provision.service.flag}")
    protected Boolean provisionServiceFlag;

    @Resource(name = "provisionServiceClient")
    protected ProvisionService provisionService;

    @Resource(name = "asynchProvisionServiceClient")
    protected AsynchUserProvisionService asynchUserProvisionService;

    @Resource(name = "userServiceClient")
    protected UserDataWebService userDataWebService;


    protected HashMap<String, List<KeyNameBean>> convertToOrganizationMap(List<Organization> organizationList) {
        HashMap<String, List<KeyNameBean>> map = new HashMap<String, List<KeyNameBean>>();
        if(organizationList!=null && !organizationList.isEmpty()){
            for(Organization org: organizationList){
                if(org.getParentOrganizations()!=null && !org.getParentOrganizations().isEmpty()){
                    KeyNameBean bean = new KeyNameBean(org.getId(), org.getName());
                    for (Organization parent: org.getParentOrganizations()){
                        if(!map.containsKey(parent.getId())){
                            map.put(parent.getId(), new ArrayList<KeyNameBean>());
                        }
                        map.get(parent.getId()).add(bean);
                    }
                }
            }
        }
        return map;
    }

    protected List<KeyNameBean> getInitialsItems(List<String> parents, HashMap<String, List<KeyNameBean>> dependedMap) {
        List<KeyNameBean> result= new ArrayList<KeyNameBean>();

        if(parents!=null && !parents.isEmpty()){
            for (String p: parents){
                if (dependedMap.containsKey(p))
                    result.addAll(dependedMap.get(p));
            }
        }
        return result;
    }
}
