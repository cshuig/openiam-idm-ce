import org.openiam.idm.groovy.helper.ServiceHelper;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService
import org.openiam.idm.srvc.msg.dto.NotificationRequest
import org.openiam.idm.srvc.msg.service.MailService
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.idm.srvc.user.ws.UserDataWebService
import org.openiam.provision.dto.ProvisionUser
import org.openiam.provision.service.ProvisionService
import org.openiam.idm.searchbeans.UserSearchBean
import org.openiam.base.id.UUIDGen;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.msg.dto.NotificationParam;
import org.openiam.idm.srvc.auth.dto.Login;

def MailService mailService = ServiceHelper.emailService();
def UserDataWebService userManager =context.getBean("userWS");
def ProvisionService provision = ServiceHelper.povisionService();
def LoginDataWebService loginManager = ServiceHelper.loginService();

println("ProvisionOnStartDate.groovy called.");

UserSearchBean search = new UserSearchBean();
search.userStatus = UserStatusEnum.PENDING_START_DATE.getValue();
userList = userManager.findBeans(search,-1,-1);
println("^^userList size = " + userList?.size());
if (userList != null) {
    for (user in userList) {

if (user.startDate==null || user.startDate.after(new Date())) {
    continue;
}
ProvisionUser pUser = new ProvisionUser(user);
pUser.id = user.id
pUser.status = UserStatusEnum.ACTIVE
pUser.secondaryStatus = null;
pUser.setProvisionOnStartDate(false);
pUser.lastUpdatedBy = "3000"
provision.modifyUser(pUser)

// get the primary identity and its password to notify the user
Login userIdentity = loginManager.getPrimaryIdentity(user.id).principal;
String decPassword =  (String)(loginManager.decryptPassword(user.id,userIdentity.password).responseValue);

// setup email notification    
NotificationRequest req = new NotificationRequest();
req.userId  = user.id;
req.notificationType = "NEW_USER_EMAIL";
req.paramList.add(new NotificationParam("identity", userIdentity.login, userIdentity.login));
req.paramList.add(new NotificationParam("password", decPassword, decPassword));
println("send Notification to "+ userIdentity?.login);
mailService.sendNotification(req);
    }
}

output = 0
