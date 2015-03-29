package org.openiam.ui.security.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.openiam.ui.security.model.XSSPatternProcessor;
import org.openiam.ui.security.model.XSSPatternRule;
import org.openiam.ui.util.OpenIAMConstants;
import org.openiam.ui.util.XSSUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.ValidationException;
import org.owasp.esapi.filters.SecurityWrapperRequest;

/**
 * This class is the primary "XSS" filter used on the site.
 * It extends the ESAPI SecurityWrapperRequest, so that *in-case* anything goes wrong
 * in that class, we can override it.
 * 
 * DO NOT modify & commit this class without doing a THOROUGH test of the ENTIRE website.
 * @author lbornov2
 *
 */
public class OpeniamHttpServletRequest extends SecurityWrapperRequest {
	
	public static final String SKIP_XSS = "SKIP_XSS";
	private XSSPatternRule xssRule = null;
	private static Logger LOG = Logger.getLogger(OpeniamHttpServletRequest.class);
	
	public OpeniamHttpServletRequest(final HttpServletRequest request, final XSSPatternRule xssRule) {
		super(request);
		this.xssRule = xssRule;
	}
	
	private XSSPatternProcessor getProcessor(final String name) {
		return (name != null && xssRule != null && xssRule.getRules() != null) ? xssRule.getRules().get(name) : null;
	}
	
	private HttpServletRequest getHttpServletRequest() {
		return ((HttpServletRequest)super.getRequest());
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return getHttpServletRequest().getRequestDispatcher(path);
	}
	
	@Override
	public String getPathInfo() {
		return getHttpServletRequest().getPathInfo();
	}
	
	@Override
	public String getContextPath() {
		return getHttpServletRequest().getContextPath();
	}
	
	@Override
	public String getRequestedSessionId() {
		return getHttpServletRequest().getRequestedSessionId();
	}
	
	@Override
	public String getRequestURI() {
		return getHttpServletRequest().getRequestURI();
	}
	
	@Override
	public StringBuffer getRequestURL() {
		return getHttpServletRequest().getRequestURL();
	}
	
	@Override
	public String getScheme() {
		return getHttpServletRequest().getScheme();
	}
	
	@Override
	public String getServerName() {
		return getHttpServletRequest().getServerName();
	}
	
	@Override
	public int getServerPort() {
		return getHttpServletRequest().getServerPort();
	}
	
	@Override
	public String getServletPath() {
		return getHttpServletRequest().getServletPath();
	}
	
	@Override
	public HttpSession getSession() {
		return getHttpServletRequest().getSession();
	}
	
	@Override
	public HttpSession getSession(boolean create) {
		return getHttpServletRequest().getSession(create);
	}
	
	@Override
	public Principal getUserPrincipal() {
		return getHttpServletRequest().getUserPrincipal();
	}
	
	@Override
	public boolean isSecure() {
		return getHttpServletRequest().isSecure();
	}
	
	@Override
	public boolean isUserInRole(String role) {
		return getHttpServletRequest().isUserInRole(role);
	}
	
	@Override
	public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
		getHttpServletRequest().setCharacterEncoding(enc);
	}
	
	@Override
	public String getRemoteUser() {
        return getHttpServletRequest().getRemoteUser();
    }
	
	@Override
	public Cookie[] getCookies() {
		return getHttpServletRequest().getCookies();
	}
	
	@Override
	public String getHeader(String name) {
		return getHttpServletRequest().getHeader(name);
	}
	
	@Override
	public Enumeration getHeaderNames() {
		return getHttpServletRequest().getHeaderNames();
	}
	
	@Override
	public Enumeration getHeaders(String name) {
		return getHttpServletRequest().getHeaders(name);
	}
	
	@Override
	public String getQueryString() {
		if(xssRule != null) {
			return getHttpServletRequest().getQueryString();
		} else {
			return super.getQueryString();
		}
	}
	
	@Override
	public String getParameter(String name) {
		final XSSPatternProcessor processor = getProcessor(name);
		if(xssRule != null && xssRule.isIgnoreXSS()) {
			return getHttpServletRequest().getParameter(name);
		} else if(processor != null) {
			return processor.process(getHttpServletRequest().getParameter(name));
		} else {
			final String orig = getHttpServletRequest().getParameter(name);
	        String clean = null;
	        try {
	            clean = ESAPI.validator().getValidInput("HTTP parameter name: " + name, orig, "HTTPParameterValue", 2000, true, false);
	        } catch (ValidationException e) {
	            
	        }
	        return clean;
		}
	}
	
	@Override
	public Map getParameterMap() {
        @SuppressWarnings({"unchecked"})
        final Map<String,String[]> map = getHttpServletRequest().getParameterMap();
        final Map<String,String[]> cleanMap = new HashMap<String,String[]>();
        for (final Object o : map.entrySet()) {
            try {
            	final Map.Entry e = (Map.Entry) o;
            	final String name = (String) e.getKey();
            	final XSSPatternProcessor processor = getProcessor(name);
            	
            	
            	final String cleanName = ESAPI.validator().getValidInput("HTTP parameter name: " + name, name, "HTTPParameterName", 100, true);

            	final String[] value = (String[]) e.getValue();
            	final String[] cleanValues = new String[value.length];
                for (int j = 0; j < value.length; j++) {
                	String cleanValue = null;
                	if(xssRule != null && xssRule.isIgnoreXSS()) {
                		cleanValue = value[j];
                	} else if(processor != null) {
                		cleanValue = processor.process(value[j]);
                	} else {

                		//Lev Bornovalov
                		//ESAPI.validator().getValidInput() will actually DECODE the value.  
                		//This means that the value is actually double-decoded, which is not what we want
                		//To avoid this, we have to re-endode the value.
                		//We COULD pass 'false' as the last argument.  However, if you look at the ESAPI SecurityWrapper,
                		//you will see that they will still double-encode our parameters in the super() methods, which
                		//is absolutely retarded
                		cleanValue = ESAPI.validator().getValidInput("HTTP parameter value: " + value[j], value[j], "HTTPParameterValue", 2000, true, false);
                	}
                    cleanValues[j] = cleanValue;
                }
                cleanMap.put(cleanName, cleanValues);
            } catch (ValidationException e) {
                // already logged
            }
        }
        return cleanMap;
    }
	
	@Override
	public String[] getParameterValues(String name) {
		String[] values = null;
		
		final XSSPatternProcessor processor = getProcessor(name);
		if(xssRule != null && xssRule.isIgnoreXSS()) {
			values = getHttpServletRequest().getParameterValues(name);
		} else if(processor != null) {
			values = processor.process(getHttpServletRequest().getParameterValues(name));
		} else {
			values = getHttpServletRequest().getParameterValues(name);

			if(values != null) {
				final List<String> newValues = new ArrayList<String>();
		        for (final String value : values) {
		            try {
		                final String cleanValue = ESAPI.validator().getValidInput("HTTP parameter value: " + value, value, "HTTPParameterValue", 2000, true, false);
		                newValues.add(cleanValue);
		            } catch (ValidationException e) {
		                LOG.warn("Failure", e);
		            }
		        }
		        values = newValues.toArray(new String[newValues.size()]);
			}
		}
		return values;
	}
}
