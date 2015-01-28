import org.openiam.idm.srvc.user.domain.UserEntity;

def UserEntity toNotify = req.getNotificationParam("TO_NOTIFY").valueObj
def UserEntity targetUser = req.getNotificationParam("TARGET_USER").valueObj
def UserEntity requestor = req.getNotificationParam("REQUESTOR").valueObj
def String requestName = req.getNotificationParam("REQUEST_REASON").valueObj
def String requestDescription = req.getNotificationParam("REQUEST_DESCRIPTION").valueObj
def String comment = req.getNotificationParam("COMMENT").valueObj

emailStr = "Dear " + toNotify.firstName + " " + toNotify.lastName + ": \n\n" +
 	"The following request has been rejected:\n\n" +
 	"Request Name: " + requestName + "\n\n" +
 	"Request Description: " + requestDescription + "\n\n" +
 	"Comment by approver:: " + comment + "\n\n"
output= emailStr