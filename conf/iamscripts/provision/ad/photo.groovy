import org.openiam.idm.srvc.user.ws.UserDataWebService

output = null

def userDataWebService = context.getBean('userWS') as UserDataWebService
def profilePicture = userDataWebService?.getProfilePictureByUserId(user.id, '3000')
if (profilePicture) {
    output = profilePicture.picture
}


