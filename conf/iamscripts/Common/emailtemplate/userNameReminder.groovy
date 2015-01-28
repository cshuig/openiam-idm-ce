def String identity = req.getNotificationParam("identity").valueObj
def String firstName = req.getNotificationParam("firstName").valueObj
def String lastName = req.getNotificationParam("lastName").valueObj



emailStr = "Dear " + firstName + " " + lastName + ": \n\n" +
        "\n\n" +
        "Your login Id is: " + identity

output=emailStr
