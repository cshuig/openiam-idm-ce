package org.openiam.ui.security;

import org.apache.log4j.Logger;
import org.openiam.idm.srvc.policy.dto.ITPolicy;
import org.openiam.idm.srvc.policy.service.PolicyDataService;
import org.openiam.idm.srvc.pswd.service.ChallengeResponseWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.web.util.UsePolicyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class OpenIAMAuthenticationDetailsSource extends WebAuthenticationDetailsSource {

	private static Logger LOG = Logger.getLogger(OpenIAMAuthenticationDetailsSource.class);
	
	@Autowired
	private OpenIAMCookieProvider cookieProvider;
	
	@Resource(name = "challengeResponseServiceClient")
	private ChallengeResponseWebService challengeResponseServiceClient;

    @Resource(name = "policyServiceClient")
    private PolicyDataService policyDataService;

    @Resource(name = "userServiceClient")
    protected UserDataWebService userDataWebService;


    public OpenIAMAuthenticationDetailsSource() {
		super();
	}

	/* no worries - this will be called once - on login */
	@Override
	public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
		boolean isQuestionsAnswered = false;
        boolean isUsePolicyConfirmed = true;

		final String userId = cookieProvider.getUserIdFromCookie(request);
		if(userId != null) {
            try {
                isQuestionsAnswered = challengeResponseServiceClient.isUserAnsweredSecurityQuestions(userId);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }

            ITPolicy itPolicy = policyDataService.findITPolicy();
            final User user = userDataWebService.getUserWithDependent(userId, null, false);
            if (itPolicy != null && user != null) {
                Boolean status = UsePolicyHelper.getUsePolicyStatus(itPolicy, user);
                if (status != null) {
                    isUsePolicyConfirmed = UsePolicyHelper.getUsePolicyStatus(itPolicy, user);
                }
            }
        }
		
		final OpeniamWebAuthenticationDetails details = new OpeniamWebAuthenticationDetails(request);
		details.setIdentityQuestionsAnswered(isQuestionsAnswered);
        details.setUsePolicyConfirmed(isUsePolicyConfirmed);
		
		LOG.debug(String.format("Authentication Details After Building: %s", details));
		return details;
	}
}
