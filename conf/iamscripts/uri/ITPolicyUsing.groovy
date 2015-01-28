import org.apache.commons.httpclient.util.URIUtil
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.time.DateUtils
import org.openiam.am.srvc.groovy.URIFederationGroovyProcessor
import org.openiam.idm.searchbeans.UserSearchBean
import org.openiam.idm.srvc.policy.dto.ITPolicy
import org.openiam.idm.srvc.policy.dto.ITPolicyApproveType
import org.openiam.idm.srvc.policy.service.PolicyDataService
import org.openiam.idm.srvc.pswd.service.ChallengeResponseWebService
import org.openiam.idm.srvc.user.dto.User;
import java.net.URI;
import java.util.List;
import org.openiam.am.srvc.dto.ContentProvider;
import org.openiam.am.srvc.dto.URIPattern;
import org.openiam.am.srvc.dto.URIPatternMetaValue;
import org.openiam.idm.srvc.user.domain.UserEntity;
import org.openiam.idm.srvc.user.dto.UserAttribute;
import org.openiam.idm.srvc.user.ws.UserDataWebService;

class ITPolicyUsing extends URIFederationGroovyProcessor {
    @Override
    public String getValue(String userId, ContentProvider contentProvider,
                           URIPattern pattern, URIPatternMetaValue metaValue, URI uri) {
	if (userId==null)
		return null;
        println("*** Call: EULA + ChallangeResponse");
        UserDataWebService userWS = ((UserDataWebService) context.getBean("userWS"));
        PolicyDataService policyServiceClient = ((PolicyDataService) context.getBean("policyDataService"));
        ITPolicy itPolicy = policyServiceClient.findITPolicy();
        UserSearchBean usb = new UserSearchBean();
        usb.setKey(userId);
        Boolean isEULA = false;
        boolean isChallangeResponse = getUseChallangeRespose(userId);
        List<User> u = userWS.findBeans(usb, 0, 1);
        if (u && u.size() > 0) {
            println("***EULA + ChallangeResponse. User Founded!")
            isEULA = getUsePolicyStatus(itPolicy, u.get(0));
        } else {
            println("***EULA + ChallangeResponse. User NOT Founded!")
        }
        println("***EULA + ChallangeResponse. SourcePattern=" + pattern.getPattern());
        StringBuilder sb = new StringBuilder();
        if (!isChallangeResponse) {
            sb.append("/selfservice/challengeResponse.html?postbackUrl=");
        }
        if (!isChallangeResponse && (isEULA != null && isEULA)) {
            sb.append(pattern.getPattern());
        } else if (isEULA != null && !isEULA){
            sb.append("/selfservice/usePolicy.html?postbackUrl=" + pattern.getPattern());
        }
        String uriH = null;
        if (StringUtils.isNotBlank(sb.toString())) {
            uriH = URIUtil.encodeQuery(sb.toString());
        }
        println("***EULA + ChallangeResponse. result =" + uriH);
        return uriH;
    }

    private Boolean getUsePolicyStatus(final ITPolicy itPolicy, final User user) {
        Boolean status = null;
        if (itPolicy != null && itPolicy.isActive()) {
            status = false;
            Date date = user.getDateITPolicyApproved();
            if (date != null && date.after(itPolicy.getCreateDate())) {
                if (itPolicy.getApproveType() == ITPolicyApproveType.ANNUALLY) {
                    Date compareDate = user.getDateITPolicyApproved();
//                    Date compareDate = itPolicy.getCreateDate();
                    status = new Date().before(DateUtils.addYears(compareDate, 1));
                } else if (itPolicy.getApproveType() == ITPolicyApproveType.ONCE) {
                    status = true;
                }
            }
        }
        return status;
    }

    private boolean getUseChallangeRespose(String userId) {
        ChallengeResponseWebService challengeResponseServiceClient = ((ChallengeResponseWebService) context.getBean("challengeResponse"));
        boolean isQuestionsAnswered = challengeResponseServiceClient.isUserAnsweredSecurityQuestions(userId);
        println("***  ChallangeResponseUsing. RetVal= " + String.valueOf(isQuestionsAnswered));
        return isQuestionsAnswered;
    }
}

