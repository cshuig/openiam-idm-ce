import org.openiam.idm.srvc.user.dto.UserAttribute


UserAttribute attr = user.getUserAttributes().get("thumbnailPhoto");
if (attr != null && attr.valueAsBytes != null ) {
    output =  attr.valueAsBytes;
}