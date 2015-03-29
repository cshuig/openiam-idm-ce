import org.openiam.idm.srvc.auth.login.LoginDataService

//def EXCHANGE_MANSYS_ID = '2c94b25748eaf9ef01492d5312d3026d'
def EXCHANGE_MANSYS_ID = '297efd354aed4c89014aed5e4c73000a' //TODO: uncomment and remove this!

output = null

if (user.alternateContactId) {
    def loginManager = context.getBean("loginManager") as LoginDataService
    def identity = loginManager.getByUserIdManagedSys(user.alternateContactId, EXCHANGE_MANSYS_ID)
    if (identity) {
        output = identity.login
    }
}

