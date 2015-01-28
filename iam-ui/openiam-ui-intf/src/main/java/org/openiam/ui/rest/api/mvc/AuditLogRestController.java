package org.openiam.ui.rest.api.mvc;

import org.openiam.ui.rest.api.model.AuditLogSearchFilter;
import org.springframework.stereotype.Controller;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuditLogRestController extends AbstractController {

    @RequestMapping(value = "/auditlog/targetTypes", method = RequestMethod.GET)
    public @ResponseBody
    AuditLogSearchFilter searchFilter(final HttpServletRequest request) {
        final AuditLogSearchFilter model = new AuditLogSearchFilter();
        model.setAuditTargetTypes(getAuditTargetTypesKeyNames());
        model.setAuditTargetActions(getActionsKeyNames());
        model.setAuditTargetStatus(getStatusCodeTypeKeyNames());
        model.setManagedSystems(getManagedSystemsAsKeyNameBeans());
        return model;
    }

}
