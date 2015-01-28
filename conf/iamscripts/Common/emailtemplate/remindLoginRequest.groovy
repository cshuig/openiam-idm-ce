
def String login = req.getNotificationParam("LOGIN").valueObj


emailStr = "Dear " + user.firstName + " " + user.lastName + ": \n\n" +
 	"This is an automatically generated email - please do not reply to it.\n\n" +
	"Your login at OpenIAM is ${login}\n\n" + 
 	"\n\n" +
 	"If you did not create this request, then please contact your security administrator. \n" 	
 	
output=emailStr