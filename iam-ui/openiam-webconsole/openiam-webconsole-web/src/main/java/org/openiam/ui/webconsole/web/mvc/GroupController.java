package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.GroupSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.dto.IdentityDto;
import org.openiam.idm.srvc.auth.ws.IdentityWebService;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.provision.dto.ProvisionGroup;
import org.openiam.provision.service.ObjectProvisionService;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractGroupController;
import org.openiam.ui.webconsole.web.provider.MenuNodeViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class GroupController extends AbstractGroupController {
	
	private static final Log log = LogFactory.getLog(GroupController.class);
	
	@Value("${org.openiam.ui.landingpage.group.root.name}")
	private String groupRootMenuName;
	
	@Value("${org.openiam.ui.group.edit.menu.name}")
	private String groupEditMenuName;



	@Autowired
	private MenuNodeViewProvider menuNodeViewProvider;

	@Override
	protected String getRootMenu() {
		return groupRootMenuName;
	}

	@Override
	protected String getEditMenu() {
		return groupEditMenuName;
	}
	

	@Override
	protected BasicAjaxResponse doEdit(final HttpServletRequest request, final HttpServletResponse response, final Group group) throws Exception {
		return doEdit(request,  response,  group, null);
	}

	@RequestMapping(value="/groupMenuTree", method=RequestMethod.GET)
	public String groupMenuTree(final HttpServletRequest request,
								final HttpServletResponse response,
								final @RequestParam(value="id") String groupId) throws IOException {
        String requesterId = getRequesterId(request);

		final GroupSearchBean searchBean = new GroupSearchBean();
		searchBean.setKey(groupId);
		searchBean.setDeepCopy(false);
		
		final List<Group> beans = groupServiceClient.findBeans(searchBean,requesterId, 0, 1);
		if(CollectionUtils.isEmpty(beans)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Group with ID '%s' not found", groupId));
			return null;
		}
		
		final Group group = beans.get(0);
		setMenuTree(request, groupEditMenuName);
		return menuNodeViewProvider.menuTreeEntitlementsRequest(request, "group", groupId, group.getName());
	}



	
}
