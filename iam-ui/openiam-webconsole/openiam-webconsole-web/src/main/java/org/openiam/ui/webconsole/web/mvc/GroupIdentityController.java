package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseCode;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.auth.dto.IdentityDto;
import org.openiam.idm.srvc.auth.dto.LoginStatusEnum;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.provision.dto.ProvisionGroup;
import org.openiam.provision.service.ObjectProvisionService;
import org.openiam.ui.rest.api.model.IdentityBean;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class GroupIdentityController extends AbstractController {

    private static final Log log = LogFactory.getLog(GroupIdentityController.class);

    @Value("${org.openiam.ui.group.edit.menu.name}")
    private String groupEditMenuName;

    @Resource(name = "groupProvisionServiceClient")
    protected ObjectProvisionService<ProvisionGroup> groupProvisionService;

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

    @RequestMapping(value="/getGroupIdentities", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getGroupIdentities(final @RequestParam(required=true, value="id") String groupId) {

        final List<IdentityDto> identityList = identityWebService.getIdentities(groupId);
        final List<IdentityBean> identityBeans = new LinkedList<>();
        if(CollectionUtils.isNotEmpty(identityList)) {
            final Map<String, KeyNameBean> managedSysMap = getManagedSysMap();
            for(final IdentityDto identity : identityList) {
                final IdentityBean identityBean = new IdentityBean(identity);
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

    @RequestMapping(value="/editGroupIdentity", method = RequestMethod.GET)
    public String getEditPage(final HttpServletRequest request,
                              final HttpServletResponse response,
                              Model model,
                              final @RequestParam(required=true, value="id") String groupId) throws IOException {
        final Group group = groupServiceClient.getGroup(groupId, getRequesterId(request));
        if(group == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        List<IdentityDto> identityDtoList = identityWebService.getIdentities(groupId);
        final List<KeyNameBean> managedSysList = getManagedSysList();

        request.setAttribute("managedSystems", (managedSysList != null) ? jacksonMapper.writeValueAsString(managedSysList) : null);
        request.setAttribute("identities", identityDtoList);
        request.setAttribute("group", group);
        request.setAttribute("dateFormatDP", DateFormatStr.getDpDate());

        setMenuTree(request, groupEditMenuName);
        return "jar:groups/identityGroup";
    }

    @RequestMapping(value="/editGroupIdentity", method = RequestMethod.POST)
    public String editGroupIdentity(final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   final @RequestBody IdentityBean identityBean) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Errors error = null;

        try {

            IdentityDto identity = null;
            if (identityBean.getId() != null) {
                identity = identityWebService.getIdentityByManagedSys(identityBean.getReferredObjectId(),
                        identityBean.getManagedSysId());
            }

            if(identity == null) {
                error = Errors.IDENTITY_DOES_NOT_EXIST;
            } else {

                identity.setIdentity(identityBean.getIdentity());
                identity.setStatus(identityBean.getStatus());

                Response wsResponse = identityWebService.isValidIdentity(identity);
                if (wsResponse.isSuccess()) {
                    Group group = groupServiceClient.getGroup(identityBean.getReferredObjectId(), getRequesterId(request));
                    if (group == null) {
                        error = Errors.OBJECT_DOES_NOT_EXIST;
                    } else {
                        wsResponse = groupProvisionService.modifyIdentity(identity);
                    }
                }
                if (wsResponse.isFailure()) {
                    if (wsResponse.getErrorCode() == ResponseCode.IDENTITY_EXISTS) {
                        error = Errors.IDENTITY_TAKEN;
                    } else {
                        error = Errors.COULD_NOT_SAVE_IDENTITY;
                    }
                }
            }
        } catch(Throwable e) {
            log.error("Can't save identity", e);
            error = Errors.COULD_NOT_SAVE_IDENTITY;
        } finally {
            if(error != null) {
                ajaxResponse.addError(new ErrorToken(error));
                ajaxResponse.setStatus(500);
            } else {
                ajaxResponse.setStatus(200);
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.IDENTITY_SAVED));
            }
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value="/deleteGroupIdentity", method = RequestMethod.POST)
    public String deleteGroupIdentity(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final @RequestParam(value="id", required=true) String id) throws IOException {

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        Errors error = null;
        try {
            IdentityDto identity = identityWebService.getIdentity(id);
            if (identity==null) {
                error = Errors.IDENTITY_DOES_NOT_EXIST;
            } else if (defaultManagedSysId.equals(identity.getManagedSysId())) {
                error = Errors.IDENTITY_CANNOT_DELETE;
            } else {
                Group group = groupServiceClient.getGroup(identity.getReferredObjectId(), getRequesterId(request));
                if (group == null) {
                    error = Errors.OBJECT_DOES_NOT_EXIST;
                } else {
                    if (LoginStatusEnum.INACTIVE.equals(identity.getStatus())) {
                        identityWebService.deleteIdentity(identity.getId());
                    } else {
                        error = Errors.IDENTITY_CANNOT_DELETE;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            error = Errors.IDENTITY_CANNOT_DELETE;
        }

        if (error == null) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.IDENTITY_DELETED));
        } else {
            ajaxResponse.setStatus(500);
            ajaxResponse.addError(new ErrorToken(error));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }
}
