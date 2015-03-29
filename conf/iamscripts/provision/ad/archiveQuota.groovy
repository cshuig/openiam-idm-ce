import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.idm.srvc.user.ws.UserDataWebService

def archieve = getUserAttributeByName(user.id, "archieve")?.value
switch (archieve) {
    case "Cached - Laptop":
        output = "10GB"
        return
    case "Non-Cached - Desktop":
        output = "3GB"
        return
    default:
        output = null
}

private UserAttribute getUserAttributeByName(String userId, String attrName) {
    def userWS = context.getBean('userWS') as UserDataWebService
    def attributes = userWS.getUserAttributesInternationalized(userId, null)
    return attributes?.find { attr-> attr.name == attrName }
}

