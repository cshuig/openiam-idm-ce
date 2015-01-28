package org.openiam.ui.webconsole.web.mvc;

import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserAttribute;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.ui.rest.api.model.MetadataAttributeBean;
import org.openiam.ui.rest.api.model.MetadataElementBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class UserAttributeController extends  BaseUserController {

    @RequestMapping(value="/userAttributes", method= RequestMethod.GET)
    public String userAttributes(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final @RequestParam(value="id", required=true) String userId) throws IOException {
        final User user = userDataWebService.getUserWithDependent(userId, getRequesterId(request), false);
        if(user==null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("User with id '%s' does not exist", userId));
            return null;
        }
       
        setMenuTree(request, userEditRootMenuId);
        request.setAttribute("user", user);
        return "users/userAttributes";
    }

    @RequestMapping(value="/deleteUserAttribute", method=RequestMethod.POST)
    public String deleteUserAttribute(final HttpServletRequest request,
                                       final HttpServletResponse response,
                                       final @RequestParam(required=true, value="id") String id) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Response wsResponse;
        if(provisionServiceFlag){
        	final UserAttribute attribute = userDataWebService.getAttribute(id);
        	final User user = userDataWebService.getUserWithDependent(attribute.getUserId(), cookieProvider.getUserId(request),true);
            final ProvisionUser pUser = new ProvisionUser(user);
            pUser.setRequestorUserId(getRequesterId(request));
            for (Map.Entry<String, UserAttribute> entry : pUser.getUserAttributes().entrySet()) {
            	if (entry.getKey().equals(attribute.getName())) {
            		entry.getValue().setOperation(AttributeOperationEnum.DELETE);
            		break;
            	}
            }
            wsResponse = provisionService.modifyUser(pUser);
        } else {
        	wsResponse = userDataWebService.removeAttribute(id);
        }
        if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
        	ajaxResponse.setStatus(HttpServletResponse.SC_OK);
        	ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.USER_ATTRIBUTE_DELETED));
        } else {
        	ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	ajaxResponse.addError(new ErrorToken(Errors.USER_ATTRIBUTE_COULD_NOT_DELETE));
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value="/saveUserAttribute", method=RequestMethod.POST)
    public String saveUserAttribute(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final @RequestBody MetadataAttributeBean attribute) {
    	final UserAttribute userAttribute = mapper.mapToObject(attribute, UserAttribute.class);
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        if(!ajaxResponse.isError()) {
            Response wsResponse = null;

            if (provisionServiceFlag) {
                User user = userDataWebService.getUserWithDependent(userAttribute.getUserId(),cookieProvider.getUserId(request),true);
                ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));
                if(userAttribute.getId()==null) {
                    if (!pUser.getUserAttributes().containsKey(userAttribute.getName())) {
                        userAttribute.setOperation(AttributeOperationEnum.ADD);
                        pUser.getUserAttributes().put(userAttribute.getName(), userAttribute);
                    }

                }  else {
                    Set<Map.Entry<String, UserAttribute>> entries = pUser.getUserAttributes().entrySet();
                    for (Map.Entry<String, UserAttribute> entry : entries) {
                        if (entry.getKey().equals(userAttribute.getName())) {
                            pUser.getUserAttributes().remove(entry.getKey());
                            userAttribute.setOperation(AttributeOperationEnum.REPLACE);
                            pUser.getUserAttributes().put(userAttribute.getName(), userAttribute);
                            break;
                        }
                    }
                }
                wsResponse = provisionService.modifyUser(pUser);

            } else {
                if(userAttribute.getId()==null) {
                    wsResponse = userDataWebService.addAttribute(userAttribute);
                }  else {
                    wsResponse = userDataWebService.updateAttribute(userAttribute);
                }
            }

            if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
                ajaxResponse.setStatus(HttpServletResponse.SC_OK);
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.USER_ATTRIBUTE_SAVED));
                ajaxResponse.addContextValues("attributeId", wsResponse.getResponseValue());
            } else {
                if(wsResponse.getErrorCode() != null) {
                    switch(wsResponse.getErrorCode()) {
                        case USER_NOT_SET:
                            ajaxResponse.addError(new ErrorToken(Errors.USER_NOT_SET));
                            break;
                        case USER_ATTRIBUTE_NAME_NOT_SET:
                            ajaxResponse.addError(new ErrorToken(Errors.USER_ATTRIBUTE_NAME_NOT_SET));
                            break;
                        default:
                            ajaxResponse.addError(new ErrorToken(Errors.USER_ATTRIBUTE_COULD_NOT_SAVE));
                            break;
                    }
                } else {
                    ajaxResponse.addError(new ErrorToken(Errors.USER_ATTRIBUTE_COULD_NOT_SAVE));
                }
                ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    /*
    private List<KeyNameBean> getMetadataList(String metadataTypeId){
        List<KeyNameBean> result = new ArrayList<KeyNameBean>(); 
        final List<MetadataElement> metadataElementAry = metadataServiceClient.getMetadataElementByType( metadataTypeId);
        if(CollectionUtils.isNotEmpty(metadataElementAry)){
        	for (final MetadataElement me: metadataElementAry){
        		result.add(new KeyNameBean(me.getId(), me.getAttributeName()));
        	}
        }
        return  result;
    }

    private List<UserAttribute> toAttributeList(List<KeyNameBean> metadataElementList) {
        List<UserAttribute> attrList = new ArrayList<UserAttribute>();
        if (metadataElementList == null || metadataElementList.isEmpty()) {
            return attrList;
        }

        for (KeyNameBean elem  :metadataElementList) {
            UserAttribute attr = new UserAttribute();
            attr.setMetadataElementId(elem.getId());
            attr.setName(elem.getName());
            attrList.add(attr);
        }
        return attrList;

    }
    */

}
