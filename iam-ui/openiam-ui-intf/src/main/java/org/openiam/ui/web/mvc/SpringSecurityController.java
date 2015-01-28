package org.openiam.ui.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringSecurityController {

	/* HACK - the logout page has to his this in every single app, so as to clear out the context */
	@RequestMapping("/clearContext")
	public String clearContext(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		SecurityContextHolder.clearContext();
		return null;
	}
}
