import org.openiam.idm.srvc.auth.login.LoginDataService

if (user.email) {
	output = user.email
} else {

	def loginManager = context.getBean("loginManager") as LoginDataService
    def loginID = user.firstName + "." + user.lastName

	ctr = 1;
	def origLoginID = loginID

	while (loginManager.loginExists(loginID, sysId)) {
		loginID = origLoginID + ctr
		ctr++
	}

	def email = loginID + "@openiam.com"
	output = email

}


