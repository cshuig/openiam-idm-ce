import org.openiam.idm.util.Transliterator
import org.springframework.context.support.ClassPathXmlApplicationContext

def loginManager = context.getBean("loginManager")

loginId=Transliterator.transliterate(user.firstName + "." + user.lastName, true)

def orgLoginId = loginId
//check on length
if (orgLoginId.length() > 17) {
    loginId = orgLoginId.substring(0,17)
}
int ctr = 1;
//check on unique
if (managedSysId != null) {
    while ( loginManager.loginExists( loginId, managedSysId )) {
        if (orgLoginId.length() > 17) {
            loginId = ctr >= 10 ? orgLoginId.substring(0,16) : orgLoginId.substring(0,17)
        }
        loginId = loginId + ctr
        ctr++
    }
}

output=loginId


