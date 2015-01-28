package org.openiam.ui.selfservice.web.mvc.provider;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.dto.AuthLevel;
import org.openiam.am.srvc.uriauth.dto.URIFederationResponse;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.am.srvc.ws.URIFederationWebService;
import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.meta.dto.PageTempate;
import org.openiam.idm.srvc.meta.dto.TemplateRequest;
import org.openiam.idm.srvc.meta.ws.MetadataElementTemplateWebService;
import org.openiam.ui.util.OpenIAMConstants;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.web.filter.ContentProviderFilter;
import org.openiam.ui.web.filter.OpeniamFilter;
import org.openiam.ui.web.filter.ProxyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TemplateProvider {
	
	@Resource(name="metadataElementTemplateServiceClient")
	private MetadataElementTemplateWebService templateService;
	
	private static Logger LOG = Logger.getLogger(TemplateProvider.class);

	public PageTempate getPageTemplate(final String userId, final HttpServletRequest request) {
		final String patternId = ContentProviderFilter.getURIPatternForRequest(request);
		
		final TemplateRequest templateRequest = new TemplateRequest();
		templateRequest.setLanguageId(OpeniamFilter.getCurrentLangauge(request).getId());
		templateRequest.setRequestURI(URIUtils.getRequestURL(request));
		templateRequest.setPatternId(patternId);
		templateRequest.setUserId(userId);
		final PageTempate template = templateService.getTemplate(templateRequest);
		return template;
	}
}
