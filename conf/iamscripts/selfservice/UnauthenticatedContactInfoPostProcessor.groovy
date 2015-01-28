import org.openiam.ui.selfservice.web.groovy.UnauthenticatedContactInfoPostprocessor;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.auth.dto.Login;

public class UnauthenticatedContactInfoPostProcessor extends UnauthenticatedContactInfoPostprocessor {

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
	
	@Override
	public String validateLogin(final String login) {
		return super.validateLogin(login);
	}
}
