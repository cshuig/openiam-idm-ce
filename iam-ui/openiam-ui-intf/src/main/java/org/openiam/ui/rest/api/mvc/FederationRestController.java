package org.openiam.ui.rest.api.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openiam.am.srvc.dto.AuthLevelGrouping;
import org.openiam.ui.rest.api.model.AuthLevelGroupingBean;
import org.openiam.ui.rest.api.model.GroupSearchMetadata;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FederationRestController extends AbstractController {

	@RequestMapping(value = "/federation/authGroupings", method = RequestMethod.GET)
	public @ResponseBody BeanResponse getAuthGroups(final HttpServletRequest request) {
		final List<AuthLevelGrouping> resultList = contentProviderServiceClient.getAuthLevelGroupingList();
		final List<AuthLevelGroupingBean> beans = mapper.mapToList(resultList, AuthLevelGroupingBean.class);
		return new BeanResponse(beans, beans.size());
	}
}
