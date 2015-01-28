import org.openiam.ui.selfservice.web.groovy.ContactInfoPostprocessor;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;

public class CustomContactInfoPostprocessor extends ContactInfoPostprocessor {

	@Override
	public String validate(final EmailAddress address) {
		return super.validate(address);
	}
	
	@Override
	public String validate(final Phone phone) {
		return super.validate(phone);
	}
	
	@Override
	public String validate(final Address address) {
		return super.validate(address);
	}
}
