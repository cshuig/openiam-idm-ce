import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.idm.srvc.user.ws.UserDataWebService

def activeSync = getUserAttributeByName(user.id, "activeSync")?.value

output = null
if (activeSync.equalsIgnoreCase('On')) {
    output = 'True'
} else if (activeSync.equalsIgnoreCase('Off')) {
    output = 'False'
}

private UserAttribute getUserAttributeByName(String userId, String attrName) {
    def userWS = context.getBean('userWS') as UserDataWebService
    def attributes = userWS.getUserAttributesInternationalized(userId, null)
    return attributes?.find { attr-> attr.name == attrName }
}

