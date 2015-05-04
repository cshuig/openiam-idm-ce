def String targetURL = req.getNotificationParam("baseUrl").valueObj
def String token = req.getNotificationParam("token").valueObj
//def String lastName = req.getNotificationParam("token").valueObj
//def String firstName = req.getNotificationParam("token").valueObj

def found = user.principalList.find {login -> login.managedSysId == "0"}
String login = found.login

emailStr = "Dear " + user.firstName + " " + user.lastName + ": \n\n" +
        "A  request has been created to activate your account. Please click on the URL below to activate your account. \n" +
        "After your identity has been successfully verified, you will be able to enter a new password. \n\n" +
        "\n\n" +
		"Your login is " + login + "\n\n" +
        targetURL + "?token="  +  token + " \n\n" +
        "If you did not create this request, then please contact your security administrator. \n"

output=emailStr