package org.openiam.ui.selfservice.web.mvc.debug;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DebugController {

	@RequestMapping("/debugHttpRequest")
	public String httpRequest(final HttpServletRequest request) {
		return "debug/httpRequest";
	}
}
