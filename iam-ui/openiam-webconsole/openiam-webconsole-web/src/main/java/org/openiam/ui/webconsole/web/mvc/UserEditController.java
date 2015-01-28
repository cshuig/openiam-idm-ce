package org.openiam.ui.webconsole.web.mvc;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.idm.srvc.org.dto.OrganizationType;
import org.openiam.idm.srvc.user.dto.ProfilePicture;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.provision.dto.ProvisionActionEnum;
import org.openiam.provision.dto.ProvisionActionEvent;
import org.openiam.provision.dto.ProvisionActionTypeEnum;
import org.openiam.provision.service.ActionEventBuilder;
import org.openiam.provision.service.ProvisionServiceEventProcessor;
import org.openiam.ui.rest.api.model.EditUserModel;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserEditController extends BaseUserController {
	
	 @RequestMapping(value = "/resetSecurityQuestions", method = RequestMethod.POST)
	 public @ResponseBody BasicAjaxResponse resetSecurityQuestions(final HttpServletRequest request, final @RequestParam(value = "id", required = true) String userId) {
		 final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

		 ErrorToken errorToken = null;
		 SuccessToken successToken = null;
		 Response wsResponse = null;

         ActionEventBuilder eventBuilder = new ActionEventBuilder(
                 userId,
                 getRequesterId(request),
                 ProvisionActionEnum.RESET_SECURITY_QUESTIONS);
         ProvisionActionEvent actionEvent = eventBuilder.build();
         Response response = provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.PRE);
         if (ProvisionServiceEventProcessor.BREAK.equalsIgnoreCase((String) response.getResponseValue())) {
             return genAbortAjaxResponse(response, localeResolver.resolveLocale(request));
         }

		 try {
			 wsResponse = challengeResponseService.resetQuestionsForUser(userId);
			 successToken = new SuccessToken(SuccessMessage.SECURITY_QUESTIONS_RESET);
		 } catch(Throwable e) {
			 errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
			 log.error("Exception while resetting security questions", e);
		 } finally {
			 if(errorToken != null) {
                ajaxResponse.setStatus(500);
                errorToken.setMessage(messageSource.getMessage(
                        errorToken.getError().getMessageName(),
                        errorToken.getParams(),
                        localeResolver.resolveLocale(request)));
                ajaxResponse.addError(errorToken);

			 } else {
                ajaxResponse.setStatus(200);
                if(successToken != null) {
                    provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.POST);
                    ajaxResponse.setSuccessToken(successToken);
                    ajaxResponse.setSuccessMessage(messageSource.getMessage(
                            successToken.getMessage().getMessageName(),
                            null,
                            localeResolver.resolveLocale(request)));
                }
			 }
		 }
		 return ajaxResponse;
	 }
	 
	 @RequestMapping(value = "/newUser", method = RequestMethod.GET)
	 public String getNewUserPage(final HttpServletRequest request, 
			 					  final HttpServletResponse response, 
			 					  final Model model) throws IOException {
		 return getEditPage(request, response, model, null);
	 }

    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String getEditPage(final HttpServletRequest request,
            final HttpServletResponse response, Model model,
            final @RequestParam(required = true, value = "id") String userId)
            throws IOException {
        String requesterId = getRequesterId(request);
        EditUserModel userModel = new EditUserModel();
        final List<List<OrganizationType>> orgTypes = getOrgTypeList();
        if (StringUtils.isNotBlank(userId)) {

            final User user = userDataWebService.getUserWithDependent(userId,
                    getRequesterId(request), true);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, String
                        .format("User with id '%s' does not exist", userId));
                return null;
            }
            ProfilePicture profilePic = userDataWebService.getProfilePictureByUserId(user.getId(), requesterId);
            if (profilePic != null && profilePic.getPicture() != null) {
                ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(profilePic.getPicture()));
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
                if (iter.hasNext()) {
                    ImageReader ir = iter.next();
                    String format = ir.getFormatName();
                    String imgSrc = profilePic.getUser().getId()+"."+format.toLowerCase().replace("jpeg","jpg");
                    model.addAttribute("profilePicSrc", imgSrc);
                }
            }
            userModel = mapper.mapToObject(user, EditUserModel.class);
            setSupervisorInfo(userModel);
            setAlternateContactInfo(userModel);
            userModel.formatDates();
            setMenuTree(request, userEditRootMenuId);
        } else {
            setMenuTree(request, userRootMenuId);
            model.addAttribute("provisionFlag", provisionServiceFlag);
        }

        model.addAttribute("orgHierarchy",
                (orgTypes != null) ? jacksonMapper.writeValueAsString(orgTypes)
                        : null);
        model.addAttribute("objClassList", getUserTypes());
        model.addAttribute("emailTypeList", getEmailTypes());
        model.addAttribute("addressTypeList", getAddressTypes());
        model.addAttribute("phoneTypeList", getPhoneTypes());
        model.addAttribute("user", userModel);
        model.addAttribute("jobList", getJobCodeList());
        model.addAttribute("employeeTypeList", getUserTypeList());
        model.addAttribute("dateFormatDP", DateFormatStr.getDpDate());
        final String menuString = getInitialMenuAsJson(request, "USER_INFO_PAGE");
        model.addAttribute("initialMenu", menuString);
        return "users/editUser";
    }

    private void setSupervisorInfo(EditUserModel userModel) {
        // get supervisor information
        User supVisor = userDataWebService.getPrimarySupervisor(userModel
                .getId());
        if (supVisor != null) {
            userModel.setSupervisorId(supVisor.getId());
            userModel.setSupervisorName(supVisor.getFirstName() + " "
                    + supVisor.getLastName());
        }
    }

    private void setAlternateContactInfo(EditUserModel userModel) {
        // get the alternate contact name:
        if (userModel.getAlternateContactId() != null
                && userModel.getAlternateContactId().length() > 0) {
            User altUser = userDataWebService.getUserWithDependent(
                    userModel.getAlternateContactId(), null, false);
            if (altUser != null) {
                userModel.setAlternateContactName(altUser.getFirstName() + " "
                        + altUser.getLastName());
            }
        }
    }

    private BasicAjaxResponse genAbortAjaxResponse(Response response, Locale locale) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        if (response.isSuccess()) {
            SuccessToken successToken = new SuccessToken(SuccessMessage.OPERATION_ABORTED);
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(successToken);
            ajaxResponse.setSuccessMessage(messageSource.getMessage(
                    successToken.getMessage().getMessageName(),
                    null,locale));
        } else {
            ajaxResponse.setStatus(500);
            ErrorToken errorToken = new ErrorToken(Errors.OPERATION_ABORTED);
            errorToken.setMessage(messageSource.getMessage(
                    errorToken.getError().getMessageName(),
                    errorToken.getParams(), locale));
            ajaxResponse.addError(errorToken);
        }
        return ajaxResponse;
    }
}
