import org.openiam.idm.srvc.user.domain.UserEntity;

def UserEntity employee = req.getNotificationParam("EMPLOYEE").valueObj
def UserEntity supervisor = req.getNotificationParam("SUPERVISOR").valueObj

emailStr = "Dear " + supervisor.getDisplayName() + ": \n\n  You have a pending re-certification request for " + employee.getDisplayName() + ".\n\n";
output= emailStr