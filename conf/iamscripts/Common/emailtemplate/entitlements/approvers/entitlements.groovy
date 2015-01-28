import org.openiam.idm.srvc.user.domain.UserEntity;

def UserEntity toNotify = req.getNotificationParam("TO_NOTIFY").valueObj
def UserEntity targetUser = req.getNotificationParam("TARGET_USER").valueObj
def UserEntity requestor = req.getNotificationParam("REQUESTOR").valueObj
def String requestName = req.getNotificationParam("REQUEST_REASON").valueObj
def String requestDescription = req.getNotificationParam("REQUEST_DESCRIPTION").valueObj

emailStr = "Dear " + toNotify.firstName + " " + toNotify.lastName + ": \n\n" +
 	"The following request has been requested:\n\n" +
 	"Request Name: " + requestName + "\n\n" +
 	"Request Description: " + requestDescription + "\n\n"
output= emailStr