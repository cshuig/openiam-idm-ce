import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.idm.srvc.user.ws.UserDataWebService

def mailboxType = getUserAttributeByName(user.id, "mailboxType")?.value

switch (mailboxType) {
    case "Room":
        output = "Room"
        return
    case "Equipment":
        output = "Equipment"
        return
    case "Shared \\ Departmental":
        output = "Shared"
        return
    default:
        output = null
}

private UserAttribute getUserAttributeByName(String userId, String attrName) {
    def userWS = context.getBean('userWS') as UserDataWebService
    def attributes = userWS.getUserAttributesInternationalized(userId, null)
    return attributes?.find { attr-> attr.name == attrName }
}

