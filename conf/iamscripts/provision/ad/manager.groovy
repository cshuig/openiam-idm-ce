import org.openiam.idm.srvc.auth.dto.Login
import org.openiam.idm.srvc.user.ws.UserDataWebService

/**
 * Manager.groovy
 * Returns the name of a person immediate supervisor. Return the DN so that it works with AD
 */

def userWS = context.getBean("userWS") as UserDataWebService

output = null

// user is passed into the script as a bind variable in the service that calls this script

def superiors = userWS.getSuperiors(user.id, 0, Integer.MAX_VALUE)
if (superiors) {
    def supervisor = superiors.get(0)
    def l = supervisor.principalList?.find {Login l-> l.managedSysId == managedSysId}
    output = l?.login
}


