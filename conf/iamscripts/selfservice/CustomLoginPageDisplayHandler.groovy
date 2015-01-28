package selfservice

import org.openiam.ui.model.Hyperlink


import org.openiam.ui.login.DefaultLoginPageDisplayHandler
import org.openiam.ui.model.Hyperlink

class CustomLoginPageDisplayHandler extends DefaultLoginPageDisplayHandler {

	public CustomLoginPageDisplayHandler() {
		
	}
	
	@Override
	public List<Hyperlink> getAdditionalHyperlinks() {
        return []
	}
}
