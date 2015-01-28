package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.BaseAttribute;
import org.openiam.base.ws.Response;
import org.openiam.idm.searchbeans.LoginSearchBean;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.provision.resp.ManagedSystemViewerResponse;
import org.openiam.provision.service.ProvisionService;
import org.openiam.provision.type.ExtensibleAttribute;
import org.openiam.provision.type.ExtensibleUser;
import org.openiam.provision.type.ManagedSystemViewerBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.util.DateFormatStr;
import org.openiam.ui.webconsole.web.model.MngSysAttrBean;
import org.openiam.ui.webconsole.web.model.MngSysViewerCommand;
import org.openiam.ui.webconsole.web.model.MngSysViewerListCommand;
import org.openiam.ui.webconsole.web.model.ViewerBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MngSystemViewerController extends BaseUserController {

    @Value("${org.openiam.defaultManagedSysId}")
    protected String defaultManagedSysId;

    @Resource(name = "managedSysServiceClient")
    protected ManagedSystemWebService managedSysServiceClient;

    @Resource(name = "provisionServiceClient")
    protected ProvisionService provisionService;

    @RequestMapping(value = "/mngSysViewerList", method = RequestMethod.GET)
    public String getEditPage(final HttpServletRequest request,
                              final HttpServletResponse response, Model model,
                              final @ModelAttribute("mngSysViewerListCommand") MngSysViewerListCommand mngSysViewerListCommand,
                              final @RequestParam(required = false, value = "id") String userId) throws IOException {

        final UserSearchBean searchBean = new UserSearchBean();
        searchBean.setKey(userId);
        final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
        User user = userList.get(0);

        if(user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        LoginSearchBean lsb = new LoginSearchBean();
        lsb.setUserId(userId);
        mngSysViewerListCommand.setCount(loginServiceClient.count(lsb));
        final List<Login> loginList = loginServiceClient.findBeans(lsb,
                mngSysViewerListCommand.getPage() * mngSysViewerListCommand.getSize(),
                mngSysViewerListCommand.getSize());
        if (CollectionUtils.isNotEmpty(loginList)) {
            for (Login l : loginList) {
                if (!defaultManagedSysId.equals(l.getManagedSysId())) { //skip OpenIAM identity
                    ManagedSysDto mngSys = managedSysServiceClient.getManagedSys(l.getManagedSysId());
                    if (mngSys != null) {
                        MngSysViewerCommand mngSysViewerCommand = new MngSysViewerCommand();
                        mngSysViewerCommand.setManagedSysId(mngSys.getId());
                        mngSysViewerCommand.setManagedSysName(mngSys.getName());
                        mngSysViewerCommand.setManagedSysStatus(mngSys.getStatus());
                        mngSysViewerCommand.setPrincipalId(l.getLoginId());
                        mngSysViewerCommand.setPrincipalName(l.getLogin());
                        mngSysViewerCommand.setIsPrincipalLocked(l.getIsLocked());
                        mngSysViewerCommand.setPrincipalStatus(l.getStatus());

                        mngSysViewerListCommand.getIdentities().add(mngSysViewerCommand);
                    }
                }
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("mngSysViewerListCommand", mngSysViewerListCommand);
        model.addAttribute("dateFormatDP", DateFormatStr.getDpDate());
        setMenuTree(request, userEditRootMenuId);
        return "users/mngSysViewerList";
    }

    @RequestMapping(value = "/updateMngSysAttrs", method = RequestMethod.POST)
    public @ResponseBody
    BasicAjaxResponse updateMngSysAttrs(final HttpServletRequest request,
                                        @RequestBody final MngSysAttrBean attrBean) throws IOException {

        Login login = loginServiceClient.findById(attrBean.getPrincipalId());
        ExtensibleUser extUser = new ExtensibleUser();
        Map<String, ExtensibleAttribute> mngSysAttrsMap = attrBean.getMngSysAttrsMap();
        List<ExtensibleAttribute> attrs = new ArrayList<ExtensibleAttribute>(attrBean.getIdmAttrsMap().values());
        List<ExtensibleAttribute> attrsToDelete = new ArrayList<ExtensibleAttribute>();
        for (ExtensibleAttribute ea : attrs) {
            if (StringUtils.isBlank(ea.getValue())) {
                attrsToDelete.add(ea);
            } else if (StringUtils.isBlank(mngSysAttrsMap.get(ea.getName()).getValue())) {
                ea.setOperation(AttributeOperationEnum.ADD.getValue());
            } else if (!ea.getValue().equals(mngSysAttrsMap.get(ea.getName()).getValue())) {
                ea.setOperation(AttributeOperationEnum.REPLACE.getValue());
            } else {
                attrsToDelete.add(ea);
            }
        }
        attrs.removeAll(attrsToDelete);
        extUser.setAttributes(attrs);

        Response res;
        if (attrBean.isExist()) {
            res = provisionService.requestModify(extUser, login, getRequesterId(request));
        } else {
            res = provisionService.requestAdd(extUser, login, getRequesterId(request));
        }

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        if (res.isFailure()) {
            ajaxResponse.setStatus(500);
            Errors error = Errors.CANNOT_SAVE_USER_INFO;
            ErrorToken errorToken = new ErrorToken(error);
            errorToken.setMessage(messageSource.getMessage(
                    errorToken.getError().getMessageName(),
                    errorToken.getParams(),
                    localeResolver.resolveLocale(request)));
            ajaxResponse.addError(errorToken);
        } else {
            ajaxResponse.setStatus(200);
            SuccessToken successToken = new SuccessToken(SuccessMessage.USER_INFO_SAVED);
            ajaxResponse.setSuccessToken(successToken);
            ajaxResponse.setSuccessMessage(messageSource.getMessage(
                    successToken.getMessage().getMessageName(), null,
                    localeResolver.resolveLocale(request)));
        }

        return ajaxResponse;
    }

    @RequestMapping(value = "/mngSysViewer", method = RequestMethod.GET)
    public String getEditPage(final HttpServletRequest request,
                              final HttpServletResponse response, Model model,
                              final @RequestParam(required = false, value = "id") String userId,
                              final @RequestParam(required = true, value = "pid") String principalId) throws IOException {

        final UserSearchBean searchBean = new UserSearchBean();
        searchBean.setKey(userId);
        final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
        User user = userList.get(0);

        if(user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        Login login = loginServiceClient.findById(principalId);
        if(login == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        ManagedSysDto managedSys = managedSysServiceClient.getManagedSys(login.getManagedSysId());
        if(managedSys == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        ManagedSystemViewerResponse res = provisionService.buildManagedSystemViewer(userId, managedSys.getId());
        List<ViewerBean> viewerList = new ArrayList<ViewerBean>();
        Map<String, ExtensibleAttribute> idmAttrsMap = new HashMap<String, ExtensibleAttribute>();
        Map<String, ExtensibleAttribute> mngSysAttrsMap = new HashMap<String, ExtensibleAttribute>();
        if (res.isSuccess()) {
            List<ManagedSystemViewerBean> beans = res.getViewerBeanList();
            if (CollectionUtils.isNotEmpty(beans)) {
                for (ManagedSystemViewerBean viewerBean : beans) {
                    if (StringUtils.isNotBlank(viewerBean.getAttributeName())) {

                        if (!viewerBean.isReadOnly()) {
                            if (viewerBean.getIdmAttribute() != null) {
                                if (viewerBean.getIdmAttribute().getValue() != null ||
                                        (viewerBean.getMngSysAttribute() != null &&
                                                viewerBean.getMngSysAttribute().getValue() != null)) {
                                    idmAttrsMap.put(viewerBean.getAttributeName(), viewerBean.getIdmAttribute());
                                }
                            } else {
                                ExtensibleAttribute a = new ExtensibleAttribute(viewerBean.getAttributeName(), null);
                                a.setObjectType("USER");
                                a.setDataType("string");
                                idmAttrsMap.put(viewerBean.getAttributeName(), a);
                            }

                            if (viewerBean.getMngSysAttribute() != null) {
                                if (viewerBean.getMngSysAttribute().getValue() != null ||
                                        (viewerBean.getIdmAttribute() != null &&
                                                viewerBean.getIdmAttribute().getValue() != null)) {
                                    mngSysAttrsMap.put(viewerBean.getAttributeName(), viewerBean.getMngSysAttribute());
                                }
                            } else {
                                ExtensibleAttribute a = new ExtensibleAttribute(viewerBean.getAttributeName(), null);
                                a.setObjectType("USER");
                                a.setDataType("string");
                                mngSysAttrsMap.put(viewerBean.getAttributeName(), a);
                            }
                        }

                        ViewerBean vb = new ViewerBean();
                        vb.setAttributeName(viewerBean.getAttributeName());
                        vb.setIdmAttribute(getDisplayValue(viewerBean.getIdmAttribute()));
                        vb.setMngSysAttribute(getDisplayValue(viewerBean.getMngSysAttribute()));
                        vb.setReadOnly(viewerBean.isReadOnly());

                        if (idmAttrsMap.get(viewerBean.getAttributeName()) != null &&
                                mngSysAttrsMap.get(viewerBean.getAttributeName()) != null) {
                            vb.setChanged(!StringUtils.equals(idmAttrsMap.get(viewerBean.getAttributeName()).getValue(),
                                            mngSysAttrsMap.get(viewerBean.getAttributeName()).getValue()));
                        }
                        viewerList.add(vb);
                    }
                }
            }
        }
        model.addAttribute("idmAttrsMapAsJSON", jacksonMapper.writeValueAsString(idmAttrsMap));
        model.addAttribute("mngSysAttrsMapAsJSON", jacksonMapper.writeValueAsString(mngSysAttrsMap));
        model.addAttribute("viewerList", viewerList);
        model.addAttribute("principalId", principalId);
        model.addAttribute("managedSys", managedSys);
        model.addAttribute("mngSysUserExist", res.isExist());
        model.addAttribute("user", user);
        model.addAttribute("dateFormatDP", DateFormatStr.getDpDate());

        setMenuTree(request, userEditRootMenuId);
        return "users/mngSysViewer";
    }

    private String getDisplayValue(ExtensibleAttribute attribute) {
        if (attribute != null) {
            if (StringUtils.isNotEmpty(attribute.getValue())) {
                return attribute.getValue();

            } else if (attribute.getAttributeContainer() != null &&
                    CollectionUtils.isNotEmpty(attribute.getAttributeContainer().getAttributeList())) {
                List<String> vals = new ArrayList<String>();
                for (BaseAttribute a: attribute.getAttributeContainer().getAttributeList()) {
                    vals.add(a.getValue());
                }
                return StringUtils.join(vals, ", ");

            } else  if (CollectionUtils.isNotEmpty(attribute.getValueList())) {
                return StringUtils.join(attribute.getValueList(), ", ");
            }
        }
        return null;
    }
}
