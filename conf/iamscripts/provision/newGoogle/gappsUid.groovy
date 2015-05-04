import org.springframework.context.support.ClassPathXmlApplicationContext

def loginManager = context.getBean("loginManager")

loginID=user.firstName.toLowerCase() + "" + user.lastName.toLowerCase()+"@openiamdemo.com"


ctr = 1;
origLoginID = loginID


while ( loginManager.loginExists( loginID, "103" )) {
  strCtrSize = String.valueOf(ctr)
	loginID=origLoginID + ctr
	ctr++
}

output=loginID


