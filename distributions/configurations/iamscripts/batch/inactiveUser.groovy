// changes the status us users that have been inactive
// runs every night

import org.openiam.idm.srvc.msg.ws.NotificationRequest;
import org.openiam.idm.srvc.msg.service.MailTemplateParameters;
import org.openiam.idm.groovy.helper.ServiceHelper;


import org.openiam.idm.srvc.auth.dto.Login
import org.openiam.idm.srvc.auth.ws.LoginDataWebService
import org.openiam.idm.srvc.msg.service.MailService
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.idm.srvc.user.ws.UserDataWebService
import org.openiam.provision.dto.ProvisionUser
import org.openiam.provision.service.ProvisionService
import java.util.HashMap;

def UserDataWebService userManager = context.getBean("userWS");
def LoginDataWebService loginManager = context.getBean("loginWS");
def MailService mailService = context.getBean("mailService");
def ProvisionService provision = context.getBean("defaultProvision");

//def MailService mailService = ServiceHelper.emailService();
//def LoginDataWebService loginManager = ServiceHelper.loginService()
//def UserDataWebService userManager = ServiceHelper.userService()
//def ProvisionService provision = ServiceHelper.povisionService()

println("Inactiveuser.groovy called.");


final int INACTIVE_DAYS = 90;

def boolean isIdmAccountActive(Login lg, LoginDataWebService loginManager, int days) {

    println("isIdmAccountActive called");

    Login idmLg = loginManager.getLoginByManagedSys("USR_SEC_DOMAIN", lg.id.login, "0").principal;

    if (idmLg == null) {
        return false;
    }

    if (idmLg.lastLogin == null) {
        return false;
    }

    // check the date
    long lastLoginMilis = idmLg.lastLogin.getTime();
    long curTimeMillis = System.currentTimeMillis();
    Date startDate = new Date(curTimeMillis);

    Calendar c = Calendar.getInstance();
    c.setTime(startDate);
    c.add(Calendar.DAY_OF_YEAR, (-1 * days));

    long startTimeMilis = c.getTimeInMillis();


    if (lastLoginMilis < startTimeMilis) {

        println("last login is before startTime - check the password.  Current Date:" + startDate);

        return isPasswordStillActive(lg, curTimeMillis);

        return false;

    }
    println("account is still active");

    return true;

}

def boolean isPasswordStillActive(Login lg, long curTime) {

    println("isPasswordStillActive called");

		long expTime = 0L;
		long graceTime = 0L;
		
		if ( lg.pwdExp == null ) {
			// no expiration date has been setup
			return true;
		}

		
    expTime = lg.pwdExp.time;
    
    if (lg.gracePeriod != null) {
    	graceTime = lg.gracePeriod.time;    
    }
    


    println("Exp Time=" + expTime + " -" + lg.pwdExp);
    println("Grace Time=" + graceTime + " -" + lg.gracePeriod);

    if (graceTime > expTime) {
        expTime = graceTime;
        println("Using graceTime...");
    }

    if (curTime > expTime) {
        // password has expired
        println(" Current time is after expTime. Password has expired. account is inactive");
        return false;

    }
    println("Password is active. account is still active");
    return true;


}

// get users that have been inactive for 90 day
// if you need all users over 90, then pass in 90,0

println("Selecing inactive users..");

loginList = loginManager.getInactiveUsers(INACTIVE_DAYS, 0).principalList


if (loginList != null) {

    println("Inactive users fround. Iterating through the list" + loginList);

    for (lg in loginList) {

        println("Processing User id=" + lg.userId + " " + lg.id)

        if ("USR_SEC_DOMAIN".equalsIgnoreCase(lg.id.domainId) &&
                !isIdmAccountActive(lg, loginManager, INACTIVE_DAYS)) {


            user = userManager.getUserWithDependent(lg.userId, true).user



            if (user.status == null || (user.status != UserStatusEnum.INACTIVE &&
                    user.status != UserStatusEnum.TERMINATE && user.status != UserStatusEnum.LEAVE)) {


                ProvisionUser pUser = new ProvisionUser(user);
                pUser.userId = user.userId
                pUser.status = UserStatusEnum.INACTIVE
                pUser.secondaryStatus = null
                pUser.lastUpdatedBy = "3000"

                pUser.requestorLogin = "IDM_SERVER";
                pUser.requestorDomain = "IDM";

                provision.modifyUser(pUser)

								// setup email notification
								HashMap<String, String> mailParameters = new HashMap<String, String>();
        				mailParameters.put(MailTemplateParameters.USER_ID.value(), user.userId);
        
							
                NotificationRequest req = new NotificationRequest("ACCOUNT_INACTIVE",mailParameters);
                mailService.sendNotificationRequest(req)
            }
        } else {
            println("Skipping: " + lg.userId + " " + lg.id);
        }
    }
}

output = 1