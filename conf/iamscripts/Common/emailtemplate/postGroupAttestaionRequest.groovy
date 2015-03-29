import org.openiam.idm.srvc.user.domain.UserEntity;
import org.openiam.idm.srvc.grp.dto.Group;

def Group group = req.getNotificationParam("GROUP").valueObj
def UserEntity groupOwner = req.getNotificationParam("OWNER").valueObj

emailStr = "Dear " + groupOwner.getDisplayName() + ": \n\n  The attestation request for " + group.getName() + " has been completed.\n\n";
output= emailStr