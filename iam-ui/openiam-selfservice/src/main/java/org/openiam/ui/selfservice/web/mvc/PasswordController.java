package org.openiam.ui.selfservice.web.mvc;

import org.openiam.ui.util.URIUtils;
import org.openiam.ui.web.mvc.AbstractPasswordController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class PasswordController extends AbstractPasswordController{
	
	@Value("${org.openiam.ui.password.change.url}")
	private String changePasswordURL;
	
	@RequestMapping(value="/redirectChallengeResponse", method=RequestMethod.GET)
	public void changedPasswordSuccess(final HttpServletResponse response) throws IOException {
		response.sendRedirect(changePasswordURL);
	}
}
