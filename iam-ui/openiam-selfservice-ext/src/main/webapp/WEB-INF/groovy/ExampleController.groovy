import org.openiam.idm.searchbeans.OrganizationSearchBean
import org.openiam.idm.srvc.org.dto.Organization
import org.openiam.idm.srvc.org.service.OrganizationDataService
import org.openiam.ui.selfservice.ext.web.mvc.AbstractSelfServiceExtController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

@Controller
public class ExampleController extends AbstractSelfServiceExtController {

    @Resource(name="organizationServiceClient")
    private OrganizationDataService organizationServiceClient;

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    public String groovy(@RequestParam(value = "id", required = false) String id,
                       final HttpServletRequest request, Model model) {

        final String requestorId = getRequesterId(request);
        final OrganizationSearchBean searchBean = new OrganizationSearchBean();
        searchBean.setDeepCopy(true);
        List<Organization> organizationList = organizationServiceClient.findBeansLocalized(searchBean, requestorId, 0, Integer.MAX_VALUE, null);
        request.setAttribute("organizationList", organizationList);

        def title = "Example Page"
        request.setAttribute("pageTitle", title)
        return "/example"
    }

    @RequestMapping(value = "/pub/example", method = RequestMethod.GET)
    public String pubGroovy(@RequestParam(value = "id", required = false) String id,
                          final HttpServletRequest request, Model model) {
        return "/pub/example"
    }

}