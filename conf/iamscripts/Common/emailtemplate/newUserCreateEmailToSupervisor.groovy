
def String identity = req.getNotificationParam("identity").valueObj
def String password = req.getNotificationParam("password").valueObj

def String name = req.getNotificationParam("userName").valueObj


emailStr = "Dear " + user.firstName + " " + user.lastName + ": \n\n" +
 	"A new user account has been created for " + name +  
 	"\n\n" +
 	"The login Id is: " + identity + " \n" +
 	"The password is: " + password
 	
output=emailStr
