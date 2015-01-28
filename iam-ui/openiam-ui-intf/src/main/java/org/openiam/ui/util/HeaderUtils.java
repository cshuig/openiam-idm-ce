package org.openiam.ui.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class HeaderUtils {

	public static String getCaseInsensitiveHeader(final HttpServletRequest request, final String lowerCaseHeaderName) {
		String retVal = null;
		if(request.getHeaderNames() != null) {
			final Enumeration<String> names = request.getHeaderNames();
			while(names.hasMoreElements()) {
				final String name = names.nextElement();
				if(name != null) {
					if(name.toLowerCase().equals(lowerCaseHeaderName)) {
						retVal = request.getHeader(name);
						break;
					}
				}
			}
		}
		return retVal;
	}
}
