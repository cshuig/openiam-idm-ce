package org.openiam.ui.webconsole.web.mvc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.idm.srvc.ui.theme.dto.UITheme;
import org.openiam.ui.util.ErrorUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UIThemeController extends AbstractWebconsoleController {

	@Value("${org.openiam.ui.theme.root.menu.id}")
	private String newRootMenu;
	
	@Value("${org.openiam.ui.theme.edit.menu.id}")
	private String editRootMenu;
	
	@RequestMapping(value="/uiThemes", method=RequestMethod.GET)
	public String uiThemes(final HttpServletRequest request, final HttpServletResponse response) {
		setMenuTree(request, newRootMenu);
		return "uiTheme/search";
	}
	
	@RequestMapping(value="/newUITheme", method=RequestMethod.GET)
	public String newUITheme(final HttpServletRequest request,
							 final HttpServletResponse response) throws IOException {
		setMenuTree(request, newRootMenu);
		return getUIThemePage(request, response, null);
	}
	
	@RequestMapping(value="/editUITheme", method=RequestMethod.POST)
	public String editUIThemePost(final HttpServletRequest request, final @RequestBody UITheme theme) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final Response wsResponse = uiThemeClient.save(theme);
		if(wsResponse.isSuccess()) {
			ajaxResponse.setRedirectURL(String.format("editUITheme.html?id=%s", wsResponse.getResponseValue()));
			ajaxResponse.setStatus(200);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.UI_THEME_SAVED));
		} else {
			final List<ErrorToken> errorList = new LinkedList<ErrorToken>();
			if(wsResponse.getErrorCode() == null) {
				errorList.add(new ErrorToken(Errors.UI_THEME_SAVE_FAIL));
			} else {
				switch(wsResponse.getErrorCode()) {
					case NAME_TAKEN:
						errorList.add(new ErrorToken(Errors.UI_THEME_NAME_TAKEN));
						break;
					case NAME_MISSING:
						errorList.add(new ErrorToken(Errors.UI_THEME_NAME_REQUIRED));
						break;
					case URL_REQUIRED:
						errorList.add(new ErrorToken(Errors.UI_THEME_STYLESHEET_REQUIRED));
						break;
					case VALIDATION_ERROR:
						errorList.addAll(ErrorUtils.getESBErrorTokens(wsResponse));
	                    break;
					default:
						errorList.add(new ErrorToken(Errors.UI_THEME_SAVE_FAIL));
						break;
				}
			}
			ajaxResponse.setStatus(500);
			ajaxResponse.setErrorList(errorList);
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/deleteUITheme", method=RequestMethod.POST)
	public String deleteUITheme(final HttpServletRequest request, final @RequestParam(required=true, value="id") String id) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final Response wsResponse = uiThemeClient.delete(id);
		if(wsResponse.isSuccess()) {
			ajaxResponse.setRedirectURL("uiThemes.html");
			ajaxResponse.setStatus(200);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.UI_THEME_DELETED));
		} else {
			final List<ErrorToken> errorList = new LinkedList<ErrorToken>();
			if(wsResponse.getErrorCode() == null) {
				errorList.add(new ErrorToken(Errors.UI_THEME_DELETE_FAIL));
			} else {
				switch(wsResponse.getErrorCode()) {
					default:
						errorList.add(new ErrorToken(Errors.UI_THEME_DELETE_FAIL));
						break;
				}
			}
			ajaxResponse.setStatus(500);
			ajaxResponse.setErrorList(errorList);
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/editUITheme", method=RequestMethod.GET)
	public String editUITheme(final HttpServletRequest request,
							  final HttpServletResponse response,
							  final @RequestParam(required=true, value="id") String id) throws IOException {
		setMenuTree(request, editRootMenu);
		return getUIThemePage(request, response, id);
	}
	
	private String getUIThemePage(final HttpServletRequest request, final HttpServletResponse response, final String id) throws IOException {
		UITheme theme = new UITheme();
		if(id != null) {
			theme = uiThemeClient.get(id);
			if(theme == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
		}
		
		request.setAttribute("isNew", StringUtils.isBlank(theme.getId()));
		request.setAttribute("theme", theme);
		request.setAttribute("uiThemeAsJSON", jacksonMapper.writeValueAsString(theme));
		return "uiTheme/edit";
	}
}
