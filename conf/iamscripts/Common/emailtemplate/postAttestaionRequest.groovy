import org.openiam.idm.srvc.user.domain.UserEntity;

def UserEntity employee = req.getNotificationParam("EMPLOYEE").valueObj
def UserEntity supervisor = req.getNotificationParam("SUPERVISOR").valueObj

emailStr = "Dear " + supervisor.getDisplayName() + ": \n\n  The re-certification request for " + employee.getDisplayName() + " has been completed.\n\n";
output= emailStr