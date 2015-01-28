package org.openiam.ui.webconsole.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openiam.ui.web.util.LandingPageRequestHandler;
import org.springframework.stereotype.Component;

@Component
public class WebconsoleLandingPageRequestHandler extends LandingPageRequestHandler {

	@Override
	public void doCustom(HttpServletRequest request,
			HttpServletResponse response) {
		//nothing for webconsole
	}

}
