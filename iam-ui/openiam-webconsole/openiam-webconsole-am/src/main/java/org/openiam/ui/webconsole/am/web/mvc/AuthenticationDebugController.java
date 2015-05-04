package org.openiam.ui.webconsole.am.web.mvc;

import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationDebugController extends AbstractController {

	@RequestMapping("/authenticationDebug")
	public String authenticationDebug() {
		return "authentication/authenticationDebug";
	}
}
