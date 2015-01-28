
def String requestReason = req.getNotificationParam("REQUEST_REASON").valueObj
def String requestor = (req.getNotificationParam("REQUESTOR") != null) ? req.getNotificationParam("REQUESTOR").valueObj : null
def String targetUser = req.getNotificationParam("TARGET_USER").valueObj
def String identity = req.getNotificationParam("IDENTITY").valueObj
def String password = req.getNotificationParam("PSWD").valueObj




emailStr = "Dear " + user.firstName + " " + user.lastName + ": \n\n" +
 	"This is to notify you that the request summarized below has been approved. \n\n" + 
 	"\n\n" +
 	"Request Type: " + requestReason + " \n" +
 	"Approver: " + requestor + " \n" +
 	"For: " + targetUser + " \n\n" +
 
  "A new user account has been created for." + targetUser + "\n" + 
 	"The login Id is: " + identity + "\n" +
 	"The initial password is: " + password
 		 	
 	
output=emailStr