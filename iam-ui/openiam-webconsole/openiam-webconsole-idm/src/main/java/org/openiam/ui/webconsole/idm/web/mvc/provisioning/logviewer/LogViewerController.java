package org.openiam.ui.webconsole.idm.web.mvc.provisioning.logviewer;

import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.audit.dto.SearchAudit;
import org.openiam.idm.srvc.synch.dto.SynchConfig;
import org.openiam.idm.srvc.synch.ws.IdentitySynchWebService;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LogViewerController extends AbstractController {

    @Autowired
    private IdentitySynchWebService synchServiceWS;

    @Value("org.openiam.ui.idm.synclog.root.name")
    private String rootMenuName;

    @RequestMapping(value = "/provisioning/syncloglist", method = RequestMethod.GET)
    public String list(@Valid @ModelAttribute("logViewerListCommand") LogViewerListCommand logViewerListCommand,
            BindingResult result,
            final HttpServletRequest request,
            Model model) {

        setMenuTree(request, rootMenuName);

        if (result.hasErrors()) {
            request.setAttribute("dateFormatDP", DateFormatStr.getDpDate());
            request.setAttribute("dateFormatJSTLFull", DateFormatStr.getSdfDateTime());
            return "provisioning/logviewer/syncloglist";
        }

        SearchAudit search = new SearchAudit();
        search.setObjectTypeId("SYNCH_USER");
        search.setActionId("START");

        if (logViewerListCommand.getConfigId() != null && logViewerListCommand.getConfigId().length() > 0) {
            search.setObjectId(logViewerListCommand.getConfigId());
        }
        if (logViewerListCommand.getStartDate() != null) {
            search.setStartDate(logViewerListCommand.getStartDate());
        }
        if (logViewerListCommand.getEndDate() != null) {
            search.setEndDate(logViewerListCommand.getEndDate());
        }
        
        /*
        logViewerListCommand.setCount(auditWS.countEvents(search));
        IdmAuditLogListResponse logListResp = auditWS.searchEvents(search,
                logViewerListCommand.getPage() * logViewerListCommand.getSize(), logViewerListCommand.getSize());
		*/
        List<IdmAuditLog> logList = Collections.EMPTY_LIST;
        /*
        if (logListResp != null && logListResp.getStatus() != ResponseStatus.FAILURE) {
            logList = logListResp.getLogList();
        }
        */
        model.addAttribute("syncLogList", logList);
        model.addAttribute("dateFormatDP", DateFormatStr.getDpDate());

        return "/provisioning/logviewer/syncloglist";
    }

    @RequestMapping(value = "/provisioning/synclog", method = RequestMethod.GET)
    public String show(
            @RequestParam(value="id", required=false) String sessionId,
            Model model) {

        SearchAudit search = new SearchAudit();
        search.setSessionId(sessionId);
        /*
        IdmAuditLogListResponse logListResp = auditWS.search(search);
        if (logListResp != null && logListResp.getStatus() != ResponseStatus.FAILURE) {
            List<IdmAuditLog> logList = logListResp.getLogList();
            model.addAttribute("eventList",logList);
        }
        */
        model.addAttribute("eventList",Collections.EMPTY_LIST);

        return "/provisioning/logviewer/synclog";
    }

    @ModelAttribute("configList")
    public Map<String,String> populateConfigList() {
        Map<String,String> items = new LinkedHashMap<String,String>();
        items.put("", new StringBuilder().append("-").append(getLocalizedMessage("openiam.ui.common.value.pleaseselect", null)).append("-").toString());
        List<SynchConfig> configList =  synchServiceWS.getAllConfig().getConfigList();
        for (SynchConfig item : configList) {
            items.put(item.getSynchConfigId(), item.getName());
        }
        return items;
    }

}
