import org.openiam.ui.selfservice.web.groovy.AuthenticatedContactInfoPreprocessor;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;

public class CustomContactInfoPreprocessor extends AuthenticatedContactInfoPreprocessor {

	@Override
	public boolean isEditable(final EmailAddress address) {
		return super.isEditable(address);
	}
	
	@Override
	public boolean isEditable(final Phone phone) {
		return super.isEditable(phone);
	}
	
	@Override
	public boolean isEditable(final Address address) {
		return super.isEditable(address);
	}
	
	@Override
	public boolean isSupportsEmailCreation() {
		return super.isSupportsEmailCreation();
	}
	
	@Override
	public boolean isSupportsPhoneCreation() {
		return super.isSupportsPhoneCreation();
	}
	
	@Override
	public boolean isSupportsAddressCreation() {
		return super.isSupportsAddressCreation();
	}
}
