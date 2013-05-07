import org.openiam.idm.srvc.loc.dto.Location
import org.openiam.idm.srvc.loc.ws.LocationResponse
import org.openiam.idm.srvc.user.dto.UserAttribute

def locationService = context.getBean("locationWS");
output = "MAINOFFICE";

if (user.locationCd != null && user.locationCd.length() > 0) {
    LocationResponse locationResp = locationService.getLocation(user.locationCd);
    Location location = locationResp.location;
    if(location != null) {
       output=location.name;
    }
} else {
    UserAttribute attr = user.getUserAttributes().get("physicalDeliveryOfficeName");
    if(attr != null) {
        output=attr.value;
    }
}


