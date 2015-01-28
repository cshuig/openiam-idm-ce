package org.openiam.ui.webconsole.web.mvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openiam.idm.searchbeans.PolicySearchBean;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.policy.service.PolicyDataService;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.webconsole.web.model.PolicyBean;
import org.openiam.ui.webconsole.web.model.PolicySearchResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PolicyRestfulController extends AbstractWebconsoleController {

	@Resource(name = "policyServiceClient")
	protected PolicyDataService policyServiceClient;
	
	@Autowired
	private DozerBeanMapper beanMapper;
	
	@RequestMapping(value="/searchPolicies", method=RequestMethod.GET)
	public @ResponseBody PolicySearchResultBean findPolicies(final HttpServletRequest request,
												   final @RequestParam(required=true, value="from") int from,
												   final @RequestParam(required=true, value="size") int size,
												   final @RequestParam(required=true, value="policyType") String policyType,
												   @RequestParam(required=false, value="name") String name) {
		if (StringUtils.isNotBlank(name)) {
            if (name.charAt(0) != '*') {
                name = "*" + name;
            }

            if (name.charAt(name.length() - 1) != '*') {
                name = name + "*";
            }
        }
		
		final PolicySearchBean searchBean = new PolicySearchBean();
		searchBean.setPolicyDefId(policyType);
		searchBean.setDeepCopy(false);
		searchBean.setName(name);
		
		final int count = policyServiceClient.count(searchBean);
		final List<Policy> policyList = policyServiceClient.findBeans(searchBean, from, size);
		final List<PolicyBean> policyBeanList = beanMapper.mapToList(policyList, PolicyBean.class);
		return new PolicySearchResultBean(count, policyBeanList);
	}
}
