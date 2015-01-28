import java.util.Date
import org.openiam.idm.srvc.msg.dto.NotificationRequest;
import org.openiam.idm.srvc.msg.dto.NotificationParam;
import org.openiam.idm.srvc.msg.service.MailTemplateParameters;
import org.openiam.idm.groovy.helper.ServiceHelper;

import org.openiam.idm.srvc.auth.dto.Login
import org.openiam.idm.srvc.auth.ws.LoginDataWebService
import org.openiam.idm.srvc.msg.service.MailService
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import java.util.HashMap;


println("accountLockedNotification executing..");
def MailService mailService = ServiceHelper.emailService();
def loginManager = ServiceHelper.loginService()

println("Manager =" + loginManager)
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -1);
loginList = loginManager.getLockedUserSince(c.getTime()).principalList
if (loginList != null ) {
        for ( lg in loginList) {
         println("userId=" + lg.userId)

  		NotificationRequest req = new NotificationRequest();

				req.userId = lg.userId;
				req.notificationType = "ACCOUNT_LOCKED";
				req.paramList.add(new NotificationParam(MailTemplateParameters.USER_ID.value(), user.id,user.id));
				println("send Notification");
                mailService.sendNotification(req);
        }
}

output=0
