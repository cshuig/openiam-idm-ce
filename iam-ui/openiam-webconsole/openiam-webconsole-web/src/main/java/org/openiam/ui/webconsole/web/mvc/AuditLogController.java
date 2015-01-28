package org.openiam.ui.webconsole.web.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.OrderConstants;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.searchbeans.AuditLogSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditTarget;
import org.openiam.idm.srvc.audit.dto.AuditLogTarget;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.ws.LoginResponse;
import org.openiam.ui.rest.api.model.AuditLogBean;
import org.openiam.ui.rest.api.model.AuditLogSearchFilter;
import org.openiam.ui.rest.api.model.AuditLogSearchForm;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuditLogController extends AbstractWebconsoleController {
	
    @Value("${org.openiam.ui.landingpage.user.edit.root.id}")
    protected String userEditRootMenuId;

	@RequestMapping("/viewAuditLog")
	public String viewAuditLog(Model model) {
        final AuditLogSearchFilter metaData = new AuditLogSearchFilter();
        metaData.setAuditTargetTypes(getAuditTargetTypesKeyNames());
        metaData.setAuditTargetActions(getActionsKeyNames());
        metaData.setAuditTargetStatus(getStatusCodeTypeKeyNames());
        metaData.setManagedSystems(getManagedSystemsAsKeyNameBeans());

        model.addAttribute("metaData", metaData);
        model.addAttribute("dateFormatDP", DateFormatStr.getDpDate());

		return "log/logViewer";
	}

    @RequestMapping("/viewRecentLogRecord")
    public String viewRecentLogRecord(final HttpServletRequest request,
                                      final HttpServletResponse response,
                                      final AuditLogSearchForm auditLogSearchForm) throws IOException {
        final AuditLogSearchBean searchBean = getAuditLogSearchBean(request, auditLogSearchForm);
        final List<String> ids = auditLogService.getIds(searchBean, 0, 1);
        if (CollectionUtils.isNotEmpty(ids)) {
            return viewLogRecord(request, response, auditLogSearchForm,ids.get(0));
        }
        return null;
    }

    @RequestMapping("/viewLogRecord")
    public String viewLogRecord(
                           final HttpServletRequest request,
                           final HttpServletResponse response,
                           final AuditLogSearchForm auditLogSearchForm,
                           final @RequestParam(required=true, value="id") String id) throws IOException {

        request.setAttribute("size", auditLogSearchForm.getSize());
        request.setAttribute("from", auditLogSearchForm.getFrom());
        request.setAttribute("page", auditLogSearchForm.getPage());
        request.setAttribute("totalSize", auditLogSearchForm.getTotalSize());
        request.setAttribute("showChildren", auditLogSearchForm.isShowChildren());

        final AuditLogSearchBean searchBean = getAuditLogSearchBean(request, auditLogSearchForm);
        final List<String> searchResultIds = auditLogService.getIds(searchBean, auditLogSearchForm.getFrom(), auditLogSearchForm.getSize());

        int indxOnThePage = 0;
        String nextId = null;
        String prevId = null;

        int nextFrom = auditLogSearchForm.getFrom();
        int prevFrom = auditLogSearchForm.getFrom();
        int nextPage = auditLogSearchForm.getPage();
        int prevPage = auditLogSearchForm.getPage();
        if (searchResultIds != null) {
            for (String srid : searchResultIds) {
                if (srid.equalsIgnoreCase(id)) {
                    break;
                }
                indxOnThePage++;
            }

            if (indxOnThePage + (auditLogSearchForm.getPage() * auditLogSearchForm.getSize()) == auditLogSearchForm.getFrom()) {
                if(auditLogSearchForm.getTotalSize() > 1) {
                    nextId = searchResultIds.get(indxOnThePage + 1);
                }
                if (auditLogSearchForm.getFrom() != 0) {
                    List<String> searchIds = auditLogService.getIds(searchBean, auditLogSearchForm.getFrom() - 1, 1);
                    prevId = CollectionUtils.isNotEmpty(searchIds) ? searchIds.get(0) : null;
                    prevPage = auditLogSearchForm.getPage() - 1;
                    prevFrom = prevPage * auditLogSearchForm.getSize();
                }
            } else if ((indxOnThePage + auditLogSearchForm.getPage() * auditLogSearchForm.getSize()) == ((auditLogSearchForm.getPage() + 1) * auditLogSearchForm.getSize() - 1) && (indxOnThePage + auditLogSearchForm.getPage() * auditLogSearchForm.getSize()) < auditLogSearchForm.getTotalSize() - 1) {
                nextPage = auditLogSearchForm.getPage() + 1;
                List<String> searchIds = auditLogService.getIds(searchBean, nextPage * auditLogSearchForm.getSize(), 1);
                nextFrom = nextPage * auditLogSearchForm.getSize();
                if (CollectionUtils.isNotEmpty(searchIds)) {
                    nextId = searchIds.get(0);
                }
                prevId = searchResultIds.get(indxOnThePage - 1);
            } else if ((indxOnThePage + auditLogSearchForm.getPage() * auditLogSearchForm.getSize()) < auditLogSearchForm.getTotalSize() - 1 &&
                    indxOnThePage > 0 && indxOnThePage < auditLogSearchForm.getSize()) {
                prevId = searchResultIds.get(indxOnThePage - 1);
                nextId = searchResultIds.get(indxOnThePage + 1);
            } else if ((indxOnThePage + auditLogSearchForm.getPage() * auditLogSearchForm.getSize()) == auditLogSearchForm.getTotalSize() - 1) {
                prevId = searchResultIds.get(indxOnThePage - 1);
            }
        }
        request.setAttribute("nextFrom", nextFrom);
        request.setAttribute("prevFrom", prevFrom);
        request.setAttribute("nextPage", nextPage);
        request.setAttribute("prevPage", prevPage);
        final IdmAuditLog log = auditLogService.getLogRecord(id);
        if(log == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        List<AuditLogBean> parentsWithTargets = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(log.getParentLogs())) {
            for (IdmAuditLog ch : log.getParentLogs()) {
                IdmAuditLog chFull = auditLogService.getLogRecord(ch.getId());
                AuditLogBean bean = mapper.mapToObject(chFull, AuditLogBean.class);
                if (CollectionUtils.isNotEmpty(chFull.getTargets())) {
                    for (AuditLogTarget logTarget : chFull.getTargets()) {
                        bean.addTarget(logTarget);
                    }
                }
                parentsWithTargets.add(bean);
            }
        }


        List<AuditLogBean> auditLogBeans = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(log.getChildLogs())) {
            for (IdmAuditLog ch : log.getChildLogsSorted()) {
                IdmAuditLog chFull = auditLogService.getLogRecord(ch.getId());
                AuditLogBean bean = mapper.mapToObject(chFull, AuditLogBean.class);
                if (CollectionUtils.isNotEmpty(chFull.getTargets())) {
                    for (AuditLogTarget logTarget : chFull.getTargets()) {
                        bean.addTarget(logTarget);
                    }
                }
                auditLogBeans.add(bean);
            }
        }

        if(StringUtils.equalsIgnoreCase("user", auditLogSearchForm.getSource())) {
            setMenuTree(request, userEditRootMenuId);
            request.setAttribute("sourceId", auditLogSearchForm.getSourceId());
            request.setAttribute("source", auditLogSearchForm.getSource());
        }

        if (StringUtils.isNotEmpty(nextId)) {
            request.setAttribute("nextId", nextId);
        }
        if (StringUtils.isNotEmpty(prevId)) {
            request.setAttribute("prevId", prevId);
        }
        request.setAttribute("children", (log.getChildLogs() != null) ? jacksonMapper.writeValueAsString(auditLogBeans) : null);
        request.setAttribute("parents", (log.getParentLogs() != null) ? jacksonMapper.writeValueAsString(parentsWithTargets) : null);
        request.setAttribute("targets", (log.getTargets() != null) ? jacksonMapper.writeValueAsString(log.getTargets()) : null);
        request.setAttribute("log", log);
        request.setAttribute("logId", id);

        return "log/view";

    }

    private AuditLogSearchBean getAuditLogSearchBean(HttpServletRequest request, AuditLogSearchForm auditLogSearchForm) {
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
        request.setAttribute("userId", auditLogSearchForm.getUserId());

        if(StringUtils.isNotBlank(StringUtils.trimToNull(auditLogSearchForm.getRequestorId()))) {
            searchBean.setUserId(auditLogSearchForm.getRequestorId());
        }
        request.setAttribute("requestorId", auditLogSearchForm.getRequestorId());

        if(StringUtils.isNotBlank(StringUtils.trimToNull(auditLogSearchForm.getRequestorLogin()))) {
            LoginResponse res = loginServiceClient.getLoginByManagedSys(
                    auditLogSearchForm.getRequestorLogin(), defaultManagedSysId);
            if (res.isSuccess()) {
                String requesterId = res.getPrincipal().getUserId();
                searchBean.setUserId(requesterId);
            }

        }
        request.setAttribute("requestorLogin", auditLogSearchForm.getRequestorLogin());

        if(StringUtils.isNotBlank(auditLogSearchForm.getManagedSystem())) {
            searchBean.setManagedSysId(auditLogSearchForm.getManagedSystem());
        }
        request.setAttribute("managedSystem", auditLogSearchForm.getManagedSystem());

        if(StringUtils.isNotBlank(auditLogSearchForm.getTargetType())) {
            searchBean.setTargetType(auditLogSearchForm.getTargetType());
        }
        request.setAttribute("targetType", auditLogSearchForm.getTargetType());

        if(StringUtils.isNotBlank(auditLogSearchForm.getTargetId())) {
            searchBean.setTargetId(auditLogSearchForm.getTargetId());
        }
        request.setAttribute("targetId", auditLogSearchForm.getTargetId());

        if(StringUtils.isNotBlank(auditLogSearchForm.getSecondaryTargetType())) {
            searchBean.setSecondaryTargetType(auditLogSearchForm.getSecondaryTargetType());
        }
        request.setAttribute("secondaryTargetType", auditLogSearchForm.getSecondaryTargetType());

        if(StringUtils.isNotBlank(auditLogSearchForm.getSecondaryTargetId())) {
            searchBean.setSecondaryTargetId(auditLogSearchForm.getSecondaryTargetId());
        }
        request.setAttribute("secondaryTargetId", auditLogSearchForm.getSecondaryTargetId());

        if(StringUtils.isNotBlank(auditLogSearchForm.getAction())) {
            searchBean.setAction(auditLogSearchForm.getAction());
        }
        request.setAttribute("action", auditLogSearchForm.getAction());

        if(StringUtils.isNotBlank(auditLogSearchForm.getResult())) {
            searchBean.setResult(auditLogSearchForm.getResult());
        }
        request.setAttribute("result", auditLogSearchForm.getResult());

        if(auditLogSearchForm.getFromDate() != null && auditLogSearchForm.getFromDate() > 0l) {
            searchBean.setFrom(new Date(auditLogSearchForm.getFromDate()));
        }
        request.setAttribute("fromDate", auditLogSearchForm.getFromDate());

        if(auditLogSearchForm.getToDate() != null && auditLogSearchForm.getToDate() > 0l) {
            searchBean.setTo(new Date(auditLogSearchForm.getToDate()));
        }
        request.setAttribute("toDate", auditLogSearchForm.getToDate());
        return searchBean;
    }


}
