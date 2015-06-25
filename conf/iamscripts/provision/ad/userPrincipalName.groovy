import org.openiam.idm.util.Transliterator
import org.springframework.context.support.ClassPathXmlApplicationContext

def loginManager = context.getBean("loginManager")

loginID= Transliterator.transliterate(user.firstName + "." + user.lastName, true) + "@ad.openiamdemo.info";

output=loginID


