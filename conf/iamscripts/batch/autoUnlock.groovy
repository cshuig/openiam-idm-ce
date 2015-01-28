import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.idm.groovy.helper.ServiceHelper;

println("autoUnlock.groovy is being executed.");

ServiceHelper.loginService().bulkUnLock(UserStatusEnum.LOCKED)

output=0
