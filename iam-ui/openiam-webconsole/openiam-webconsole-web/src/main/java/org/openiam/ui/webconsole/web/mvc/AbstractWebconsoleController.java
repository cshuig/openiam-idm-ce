package org.openiam.ui.webconsole.web.mvc;


import java.util.List;

import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.res.dto.ResourceType;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

public abstract class AbstractWebconsoleController extends AbstractController {
    
}
