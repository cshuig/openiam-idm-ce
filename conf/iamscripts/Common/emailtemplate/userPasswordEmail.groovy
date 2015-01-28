def String identity = req.getNotificationParam("identity").valueObj
def String password = req.getNotificationParam("password").valueObj
def String firstName = req.getNotificationParam("firstName").valueObj
def String lastName = req.getNotificationParam("lastName").valueObj



emailStr = "Dear " + firstName + " " + lastName + ": \n\n" +
 	"Your password has been reset for you.. \n\n" + 
 	"\n\n" +
 	"Your login Id is: " + identity + " \n" +
 	"Your password is: " + password
 	
output=emailStr
