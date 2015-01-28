package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.openiam.idm.searchbeans.MetadataElementSearchBean;
import org.openiam.idm.searchbeans.MetadataTemplateTypeSearchBean;
import org.openiam.idm.searchbeans.MetadataTypeSearchBean;
import org.openiam.idm.srvc.meta.dto.MetadataElement;
import org.openiam.idm.srvc.meta.dto.MetadataTemplateType;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.meta.ws.MetadataElementTemplateWebService;
import org.openiam.idm.srvc.meta.ws.MetadataWebService;
import org.openiam.idm.srvc.res.service.ResourceDataService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseTemplateController  extends AbstractWebconsoleController {
    @Resource(name="metadataElementTemplateServiceClient")
    protected MetadataElementTemplateWebService templateService;
    @Resource(name="resourceServiceClient")
    protected ResourceDataService resourceDataService;

    protected List<MetadataTemplateType> getTemplateTypeList() {
        MetadataTemplateTypeSearchBean templateTypeSearchBean = new MetadataTemplateTypeSearchBean();
        return templateService.findTemplateTypes(templateTypeSearchBean, 0, Integer.MAX_VALUE);
    }
}
