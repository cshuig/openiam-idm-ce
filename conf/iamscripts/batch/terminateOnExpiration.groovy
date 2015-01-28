import org.openiam.base.AttributeOperationEnum
import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.idm.srvc.user.ws.UserDataWebService
import org.openiam.provision.dto.ProvisionUser
import org.openiam.provision.service.ProvisionService

import java.text.SimpleDateFormat


System.out.println("terminateOnExpiration.groovy")

def userService = context.getBean("userWS") as UserDataWebService
def provision = context.getBean("defaultProvision") as ProvisionService

def cal = Calendar.getInstance()
def userList = userService.getUserByLastDate(cal.getTime())

println("Userlist = " + userList)

if (userList) {
    for (user in userList) {

        println("User status=" + user.status)
        println("User id=" + user.id)

        if (user.status == UserStatusEnum.TERMINATED || user.secondaryStatus == UserStatusEnum.DISABLED) {
            println("User status is already set to terminate")

        } else {
            def df =  new SimpleDateFormat("yyyy/MM/dd")
            println("Terminating user " + user.firstName + " " + user.lastName + ", termination date is " + df.format(user.lastDate))
            def pUser = new ProvisionUser(user)
            String oimType = user.userAttributes?.get('OIMType')?.value?: null
            if("loa".equalsIgnoreCase(oimType)) {
                Attribute inactiveOUAttr =  new Attribute("Inactive_OU","OU=LOA,OU=Disabled Users,DC=iamdev,DC=iam");
            	addAttribute(pUser, inactiveOUAttr); 
            } else {
            	Attribute inactiveOUAttr = new Attribute("Inactive_OU","OU=Disabled Users,DC=iamdev,DC=iam");
            	addAttribute(pUser, inactiveOUAttr);
            }
            pUser.status = UserStatusEnum.TERMINATED
            pUser.secondaryStatus = UserStatusEnum.DISABLED

            provision.modifyUser(pUser)
//            provision.deleteByUserId(user.id, UserStatusEnum.TERMINATED, "0001")

        }
    }
}

def addAttribute(ProvisionUser pUser, Attribute attr) {
    if (attr?.name) {
        def userAttr = new UserAttribute(attr.name, attr.value)
        userAttr.operation = AttributeOperationEnum.ADD
        if (pUser.userId) {
            for (String name : pUser.userAttributes.keySet()) {
                if (name.equalsIgnoreCase(attr.name)) {
                    pUser.userAttributes.remove(name)
                    userAttr.operation = AttributeOperationEnum.REPLACE
                    break
                }
            }
        }
        pUser.userAttributes.put(attr.name, userAttr)
    }
}

output = 0
