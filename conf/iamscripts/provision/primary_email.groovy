import org.openiam.idm.srvc.auth.login.LoginDataService
import org.openiam.idm.util.Transliterator

if (user.email) {
	output = user.email
} else {

	def loginManager = context.getBean("loginManager") as LoginDataService
    def loginID = Transliterator.transliterate(user.firstName + "." + user.lastName, true)

	ctr = 1;
	def origLoginID = loginID

	while (loginManager.loginExists(loginID, sysId)) {
		loginID = origLoginID + ctr
		ctr++
	}

	def email = loginID + "@openiam.com"
	output = email

}


