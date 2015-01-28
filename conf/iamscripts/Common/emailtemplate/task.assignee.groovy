

def String taskName = req.getNotificationParam("TASK_NAME").valueObj
def String taskDescription = req.getNotificationParam("TASK_DESCRIPTION").valueObj
def String targetUser = req.getNotificationParam("TARGET_USER").valueObj

emailStr = "Dear " + targetUser + ": \n\n" +
	 "The following request is pending:\n\n" +
	 "Request Name: " + taskName + "\n\n" +
	 "Request Description: " + taskDescription + "\n\n" +
	 "Please sign in to complete this request \n\n"
output= emailStr