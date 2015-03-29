import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.idm.srvc.user.ws.UserDataWebService

def mailbox = getUserAttributeByName(user.id, "mailbox")?.value
switch (mailbox) {
    case "Small":
        output = "250MB"
        return
    case "Medium":
        output = "1GB"
        return
    case "Regular":
        output = "3GB"
        return
    case "Large":
        output = "10GB"
        return
    default:
        output = null
}

private UserAttribute getUserAttributeByName(String userId, String attrName) {
    def userWS = context.getBean('userWS') as UserDataWebService
    def attributes = userWS.getUserAttributesInternationalized(userId, null)
    return attributes?.find { attr-> attr.name == attrName }
}

