package org.openiam.ui.tags;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.OutSupport;
import org.apache.taglibs.standard.tag.el.core.OutTag;
import org.openiam.ui.web.filter.ContentProviderFilter;
import org.openiam.ui.web.filter.OpeniamFilter;

public class OverrideCSSTag extends  OutSupport {
	
	private static final String CSS_TEMPLATE = "<link href=\"%s\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />";
	
	public OverrideCSSTag() {
		super();
		this.def = null;
		this.escapeXml = false;
	}
	
	private static final Logger LOG = Logger.getLogger(OverrideCSSTag.class);

	@Override
	public int doStartTag() throws JspException {
		final String stylesheet = ContentProviderFilter.getOverrideStylesheet((HttpServletRequest)pageContext.getRequest());
		if(stylesheet != null) {
			this.value = String.format(CSS_TEMPLATE, stylesheet);
		}
		return super.doStartTag();
	}
}