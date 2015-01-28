package org.openiam.ui.webconsole.web.mvc;

import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.authmanager.ws.request.MenuEntitlementsRequest;
import org.openiam.authmanager.ws.response.MenuSaveResponse;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.webconsole.web.model.AuthorizationMenuRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MenuNodeController extends AbstractWebconsoleController {

	@RequestMapping(value="/saveMenuTree", method=RequestMethod.POST)
	public String saveMenuTree(final HttpServletRequest request,
							   final @RequestBody AuthorizationMenuRequest tree) {
		final BasicAjaxResponse response = new BasicAjaxResponse();
		try {
			final AuthorizationMenu menu = tree.getTree();
			final MenuSaveResponse saveResponse = (menu != null) ? authManagerMenuService.saveMenuTree(menu) : authManagerMenuService.deleteMenuTree(tree.getRootId());
			if(ResponseStatus.FAILURE.equals(saveResponse.getStatus())) {
				response.setStatus(500);
				ErrorToken token = null;
				if(saveResponse.getErrorCode() != null) {
					switch(saveResponse.getErrorCode()) {
					 	case HANGING_CHILDREN:
					 		token = new ErrorToken(Errors.MENU_DELETE_CHILDREN_FIRST, saveResponse.getProblematicMenuName());
					 		break;
					 	case HANGING_GROUPS:
					 		token = new ErrorToken(Errors.MENU_DELETE_GROUPS_FIRST, saveResponse.getProblematicMenuName());
					 		break;
					 	case HANGING_ROLES:
					 		token = new ErrorToken(Errors.MENU_DELETE_ROLES_FIRST, saveResponse.getProblematicMenuName());
					 		break;
					 	case MENU_DOES_NOT_EXIST:
					 		token = new ErrorToken(Errors.MENU_DOES_NOT_EXISTS, saveResponse.getProblematicMenuName());
					 		break;
					 	case NAME_TAKEN:
					 		token = new ErrorToken(Errors.MENU_NAME_TAKEN, saveResponse.getProblematicMenuName());
					 		break;
					 	default:
					 		token = new ErrorToken(Errors.INTERNAL_ERROR);
					 		break;
					}
				} else {
					token = new ErrorToken(Errors.INTERNAL_ERROR);
				}
				response.addError(token);
			} else {
				response.setStatus(200);
				
				/* check if the user just deleted the root.  Send to Resource Page, if so */
				if(menu == null) {
					response.setRedirectURL("resources.html");
				}
			}
		} catch(Throwable e) {
			log.error("Can't save menu", e);
			response.setStatus(200);
		}
		request.setAttribute("response", response);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/getCurrentMenuTreeForPrincipal", method=RequestMethod.GET)
	public @ResponseBody AuthorizationMenu getMenuTreeForPrincipal(final @RequestParam(value="menuId", required=true) String menuId,
																   final @RequestParam(value="principalId", required=true) String principalId,
																   final @RequestParam(value="principalType", required=true) String principalType) {
		return authManagerMenuService.getNonCachedMenuTree(menuId, principalId, principalType, getCurrentLanguage());
	}
	
	@RequestMapping(value="/saveMenuEntitlements", method=RequestMethod.POST)
	public String saveMenuEntitlements(final HttpServletRequest request, 
									   final @RequestBody MenuEntitlementsRequest entitlementsRequst) {
		final Response menuResponse = authManagerMenuService.entitle(entitlementsRequst);
		final BasicAjaxResponse response = new BasicAjaxResponse();
		if(menuResponse.getStatus().ordinal() == ResponseStatus.SUCCESS.ordinal()) {
			response.setStatus(200);
		} else {
			response.setStatus(500);
			response.addError(new ErrorToken(Errors.COULD_NOT_ENTITLE_TO_MENU));
		}
		request.setAttribute("response", response);
		return "common/basic.ajax.response";
	}
}
