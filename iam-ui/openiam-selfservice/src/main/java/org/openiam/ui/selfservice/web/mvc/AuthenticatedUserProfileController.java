package org.openiam.ui.selfservice.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.bpm.activiti.ActivitiService;
import org.openiam.exception.EsbErrorToken;
import org.openiam.idm.srvc.user.dto.ProfilePicture;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.resp.ProvisionUserResponse;
import org.openiam.provision.service.ProvisionService;
import org.openiam.ui.selfservice.web.groovy.AuthenticatedContactInfoPreprocessor;
import org.openiam.ui.selfservice.web.model.AddressBean;
import org.openiam.ui.selfservice.web.model.EmailBean;
import org.openiam.ui.selfservice.web.model.NewUserBean;
import org.openiam.ui.selfservice.web.model.PhoneBean;
import org.openiam.ui.selfservice.web.model.ProfieEditMode;
import org.openiam.ui.selfservice.web.mvc.validator.UserProfileValidator;
import org.openiam.ui.selfservice.web.util.WSUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.openiam.idm.srvc.meta.dto.SaveTemplateProfileResponse;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Controller
public class AuthenticatedUserProfileController extends AbstractProfileController {
	
    @Value("${org.openiam.profile.edit.contact.info.groovy.preprocessor}")
    private String contactInfoPreProcessorScript;
    
    @Resource(name = "provisionServiceClient")
    protected ProvisionService provisionService;
    
	@Resource(name="activitiClient")
	private ActivitiService activitiService;
    
    @PostConstruct
    public void init() {
    	if(contactInfoPreProcessorScript != null) {
    		if(!contactInfoPreProcessorScript.startsWith("/")) {
    			contactInfoPreProcessorScript = "/" + contactInfoPreProcessorScript;
    		}
    	}
    }
    
	@Autowired
	private UserProfileValidator profileValidator;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
		  binder.setValidator(profileValidator);
	}
	
	@Value("${org.openiam.selfservice.activiti.user.menu}")
	private String userMenu;
	
	@RequestMapping(value="/editUser", method = RequestMethod.GET)
	public String editUser(final HttpServletRequest request,
						   final HttpServletResponse response,
						   @RequestParam(required=true, value="id") String id) throws Exception {
		final String requestorId = cookieProvider.getUserId(request);
		final User user = userDataWebService.getUserWithDependent(id, requestorId, true);
		if(user == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		request.setAttribute("theUser", user);
		request.setAttribute("pageType", ProfieEditMode.EDIT_USER);
		setMenuTree(request, userMenu);
		return processGET(request, response, user, contactInfoPreProcessorScript);
	}
	
	
	@RequestMapping(value="/editUser", method = RequestMethod.POST)
	public String editUserPOST(final HttpServletRequest request,
			  				   final HttpServletResponse response,
			  				   final @RequestBody @Valid NewUserBean userBean) throws Exception {
		return handleAuthenticatedWorkflowPOST(request, response, userBean);
	}
	
	@RequestMapping(value="/editProfile", method=RequestMethod.GET)
	public String editProfileGET(final HttpServletRequest request, 
								 final HttpServletResponse response) throws Exception {
		request.setAttribute("pageType", ProfieEditMode.EDIT_PROFILE);
		final User user = userDataWebService.getUserWithDependent(getRequesterId(request),null, true);
		return processGET(request, response, user, contactInfoPreProcessorScript);
	}
	
	@RequestMapping(value="/editProfile", method=RequestMethod.POST)
	public String editProfilePOST(final HttpServletRequest request, 
								  final HttpServletResponse response,
								  final @RequestBody @Valid NewUserBean userBean) {
		return handleAuthenticatedPOST(request, response, userBean);
	}

    @RequestMapping(value="/editProfilePic", method = RequestMethod.GET)
    public String editProfilePic(final HttpServletRequest request,
                           final HttpServletResponse response) throws Exception {
        String requesterId = getRequesterId(request);
        final User user = userDataWebService.getUserWithDependent(requesterId,null, true);
        if(user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
                request.setAttribute("userId", user.getId());
                request.setAttribute("profilePicSrc", imgSrc);
            }
        }
        request.setAttribute("user", user);
        return "user/profilePic";
    }
	
	private String processGET(final HttpServletRequest request, 
							  final HttpServletResponse response, 
							  final User user,
							  final String preProessor) throws Exception {
		final AuthenticatedContactInfoPreprocessor preProcessor = getAuthenticatedContactInfoPreProcessor(user, request, preProessor);
		final List<EmailBean> emailList = mapper.mapToList(user.getEmailAddresses(), EmailBean.class);
		final List<PhoneBean> phoneList = mapper.mapToList(user.getPhones(),PhoneBean.class);
		final List<AddressBean> addressList = mapper.mapToList(user.getAddresses(), AddressBean.class);
		if(CollectionUtils.isNotEmpty(emailList)) {
			for(final EmailBean bean : emailList) {
				bean.preprocess(preProcessor);
			}
		}
		
		if(CollectionUtils.isNotEmpty(phoneList)) {
			for(final PhoneBean bean : phoneList) {
				bean.preprocess(preProcessor);
			}
		}
		
		if(CollectionUtils.isNotEmpty(addressList)) {
			for(final AddressBean bean : addressList) {
				bean.preprocess(preProcessor);
			}
		}
		
		final NewUserBean bean = new NewUserBean();
		bean.setUser(user);
		bean.formatDates();
		
		User alternateContact = new User();
		if(StringUtils.isNotBlank(user.getAlternateContactId())) {
			alternateContact = userDataWebService.getUserWithDependent(user.getAlternateContactId(), null, false);
		}
		request.setAttribute("alternateContact", alternateContact);
		
		request.setAttribute("emailList", (emailList != null) ? jacksonMapper.writeValueAsString(emailList) : null);
		request.setAttribute("phoneList", (emailList != null) ? jacksonMapper.writeValueAsString(phoneList) : null);
		request.setAttribute("addressList", (emailList != null) ? jacksonMapper.writeValueAsString(addressList) : null);
		request.setAttribute("userBean", bean);
		return processProfileScreenGetRequest(request, user, false, user.getId());
	}
	
	private String handleAuthenticatedWorkflowPOST(final HttpServletRequest request, final HttpServletResponse response, final NewUserBean userBean) {
		//final String description = String.format("Edit User %s", userBean.getUser().getDisplayName());
		
		userBean.formatDates();
		userBean.setRequestorUserId(getRequesterId(request));
		/*
		final UserProfileRequestModel requestModel = mapper.mapToObject(userBean, UserProfileRequestModel.class);
		
		final GenericWorkflowRequest workflowRequest = new GenericWorkflowRequest();
		workflowRequest.setActivitiRequestType(ActivitiRequestType.EDIT_USER.getKey());
		workflowRequest.setAssociationId(userBean.getUser().getUserId());
		workflowRequest.setAssociationType(AssociationType.USER);
		workflowRequest.setCallerUserId(getRequesterId(request));
		workflowRequest.setDescription(description);
		workflowRequest.setName(description);
		workflowRequest.setRequestorUserId(getRequesterId(request));
		workflowRequest.addParameter(ActivitiConstants.USER_PROFILE, new XStream().toXML(requestModel));
		*/
		final SaveTemplateProfileResponse wsResponse = activitiService.initiateEditUserWorkflow(userBean);
		
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setRedirectURL(redirectUrlAfterUserWorkflowInitialization);
			ajaxResponse.setStatus(200);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
		} else {
			Object[] params = null;
			Errors error = Errors.WORKFLOW_NOT_INITIATED;
			boolean isValidationError = false;
			if(wsResponse.getErrorCode() != null) {
				switch(wsResponse.getErrorCode()) {
					case UNAUTHORIZED:
						error = Errors.UNAUTHORIZED;
						break;
					case INVALID_VALUE:
						error = Errors.INVALID_META_VALUE;
						params = new Object[] {wsResponse.getCurrentValue(), wsResponse.getElementName()};
						break;
					case REQUIRED:
						error = Errors.META_VALUE_REQUIRED;
						params = new Object[] {wsResponse.getElementName()};
						break;
					case FIRST_NAME_REQUIRED:
						error = Errors.REQUIRED_USER_FIRST_NAME;
						break;
					case LAST_NAME_REQUIRED:
						error = Errors.REQUIRED_USER_LAST_NAME;
						break;
					case EMAIL_REQUIRED:
						error = Errors.REQUIRED_USER_EMAIL;
						break;
					case VALIDATION_ERROR:
						isValidationError = true;
	                    for (EsbErrorToken e: wsResponse.getErrorTokenList()){
	                        ErrorToken errorToken = new ErrorToken();
	                        errorToken.setValidationError(e.getMessage());
	                        if(e.getLengthConstraint()!=null) {
	                            errorToken.setParams(new Object[]{e.getLengthConstraint()});
	                        }
	
	                        ajaxResponse.addError(errorToken);
	                    }
	                    break;
					default:
						break;
				}
			}
			if(!isValidationError) {
				ajaxResponse.addError(new ErrorToken(error, params));
			}
			ajaxResponse.setStatus(500);
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	private String handleAuthenticatedPOST(final HttpServletRequest request, final HttpServletResponse response, final NewUserBean userBean) {
		final String userId = cookieProvider.getUserId(request);
		userBean.setLanguageId(getCurrentLanguage().getId());
		
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final SaveTemplateProfileResponse wsResponse = userDataWebService.saveUserProfile(userBean);
		ProvisionUserResponse provResponse = new ProvisionUserResponse();
		provResponse.setStatus(ResponseStatus.SUCCESS);
			
		/* no other way to do this w/o modifying pojo <-> idm dependencies.  First save in our system, then save in IDM */
		if(provisionServiceFlag && ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			final User user = userDataWebService.getUserWithDependent(userId, null, true);
			final ProvisionUser pUser = new ProvisionUser(user);
            pUser.setRequestorUserId(userId);
            WSUtils.setWSClientTimeout(provisionService, 360000L);
			provResponse = provisionService.modifyUser(pUser);
		}
		if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus()) && ResponseStatus.SUCCESS.equals(provResponse.getStatus())) {
			ajaxResponse.setStatus(200);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.USER_PROFILE_SAVED));
		} else {
			Object[] params = null;
			Errors error = Errors.USER_PROFILE_SAVE_FAILED;
			if(!ResponseStatus.SUCCESS.equals(provResponse.getStatus())) {
				if(provResponse.getErrorCode() != null) {
					switch(provResponse.getErrorCode()) {
						default:
							break;
					}
				}
			}
			boolean isValidationError = false;
			if(!ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
				if(wsResponse.getErrorCode() != null) {
					switch(wsResponse.getErrorCode()) {
						case UNAUTHORIZED:
							error = Errors.UNAUTHORIZED;
							break;
						case INVALID_VALUE:
							error = Errors.INVALID_META_VALUE;
							params = new Object[] {wsResponse.getCurrentValue(), wsResponse.getElementName()};
							break;
						case REQUIRED:
							error = Errors.META_VALUE_REQUIRED;
							params = new Object[] {wsResponse.getElementName()};
							break;
						case FIRST_NAME_REQUIRED:
							error = Errors.REQUIRED_USER_FIRST_NAME;
							break;
						case LAST_NAME_REQUIRED:
							error = Errors.REQUIRED_USER_LAST_NAME;
							break;
						case EMAIL_REQUIRED:
							error = Errors.REQUIRED_USER_EMAIL;
							break;
						case VALIDATION_ERROR:
							isValidationError = true;
	                        for (EsbErrorToken e: wsResponse.getErrorTokenList()){
	                            ErrorToken errorToken = new ErrorToken();
	                            errorToken.setValidationError(e.getMessage());
	                            if(e.getLengthConstraint()!=null) {
	                                errorToken.setParams(new Object[]{e.getLengthConstraint()});
	                            }

	                            ajaxResponse.addError(errorToken);
	                        }
	                        break;
						default:
							break;
					}
				}
			}
			if(!isValidationError) {
				ajaxResponse.addError(new ErrorToken(error, params));
			}
			ajaxResponse.setStatus(500);
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
}
