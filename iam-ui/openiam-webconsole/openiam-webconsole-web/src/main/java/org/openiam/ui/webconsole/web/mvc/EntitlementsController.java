package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.openiam.authmanager.model.UserEntitlementsMatrix;
import org.openiam.authmanager.service.AuthorizationManagerAdminWebService;
import org.openiam.base.OrderConstants;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.searchbeans.OrganizationSearchBean;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.openiam.idm.srvc.org.service.OrganizationTypeDataService;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.role.ws.RoleDataWebService;
import org.openiam.provision.service.ProvisionService;
import org.openiam.ui.rest.api.model.OrganizationBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EntitlementsController extends AbstractWebconsoleController {

    @Resource(name = "resourceServiceClient")
    private ResourceDataService resourceDataService;

    @Resource(name = "provisionServiceClient")
    protected ProvisionService provisionService;

    @Resource(name = "groupServiceClient")
    private GroupDataWebService groupServiceClient;

    @Resource(name = "roleServiceClient")
    private RoleDataWebService roleServiceClient;

    @Resource(name = "organizationServiceClient")
    private OrganizationDataService organizationDataService;

    @Resource(name = "authManagerAdminClient")
    private AuthorizationManagerAdminWebService authMangerAdminClient;
    
	@Resource(name="organizationTypeClient")
	private OrganizationTypeDataService organizationTypeClient;

    @RequestMapping(value = "/getUserEntitlementsMatrix", method = RequestMethod.GET)
    public @ResponseBody
    UserEntitlementsMatrix getEntitlementsMatrix(final HttpServletRequest request, @RequestParam(required = true, value = "id") String userId) {
        return authMangerAdminClient.getUserEntitlementsMatrix(userId);
    }

    @RequestMapping(value = "/addChildOrganization", method = RequestMethod.POST)
    public String addChildOrganization(final HttpServletRequest request,
                                       final @RequestParam(required = true, value = "organizationId") String organizationId,
                                       final @RequestParam(required = true, value = "childOrganizationId") String childOrganizationId) {
        final Response wsResponse = organizationDataService.addChildOrganization(organizationId, childOrganizationId);
        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, true);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeChildOrganization", method = RequestMethod.POST)
    public String removeChildOrganization(final HttpServletRequest request,
                                          final @RequestParam(required = true, value = "organizationId") String organizationId,
                                          final @RequestParam(required = true, value = "childOrganizationId") String childOrganizationId) {
        final Response wsResponse = organizationDataService.removeChildOrganization(organizationId, childOrganizationId);
        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, true);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/getChildOrganizations", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getChildOrganizations(final HttpServletRequest request, final @RequestParam(required = true, value = "id") String organizationId,
                                       final @RequestParam(required = true, value = "size") Integer size,
                                       final @RequestParam(required = true, value = "from") Integer from,
                                       final @RequestParam(required = false, value = "sortBy") String sortBy,
                                       final @RequestParam(required = false, value = "orderBy") String orderBy) {

        String requesterId = this.getRequesterId(request);

        final OrganizationSearchBean searchBean = new OrganizationSearchBean();
        searchBean.addParentId(organizationId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }

        final Integer count = (from.intValue() == 0) ? organizationDataService.count(searchBean, requesterId) : null;
        final List<Organization> results = organizationDataService.findBeansLocalized(searchBean, requesterId, from, size, getCurrentLanguage());
        return new BeanResponse(mapper.mapToList(results, OrganizationBean.class), count);

    }

    @RequestMapping(value = "/getParentOrganizations", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getParentOrganizations(final HttpServletRequest request, final @RequestParam(required = true, value = "id") String organizationId,
                                        final @RequestParam(required = true, value = "size") Integer size,
                                        final @RequestParam(required = true, value = "from") Integer from,
                                        final @RequestParam(required = false, value = "sortBy") String sortBy,
                                        final @RequestParam(required = false, value = "orderBy") String orderBy) {

        String requesterId = this.getRequesterId(request);

        final OrganizationSearchBean searchBean = new OrganizationSearchBean();
        searchBean.addChildId(organizationId);

        if(StringUtils.isNotBlank(sortBy)){
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add((orderBy != null) ? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(sortBy));
            searchBean.setSortBy(sortParamList);
        }

        final Integer count = (from.intValue() == 0) ? organizationDataService.count(searchBean, requesterId) : null;
        final List<Organization> results = organizationDataService.findBeansLocalized(searchBean, requesterId, from, size, getCurrentLanguage());
        return new BeanResponse(mapper.mapToList(results, OrganizationBean.class), count);
    }
    
	@RequestMapping(value="/organizationTypeChildAdd", method=RequestMethod.POST)
	public String organizationTypeChildAdd(final HttpServletRequest request,
								   		   final @RequestParam(required=true, value="typeId") String typeId,
								   		   final @RequestParam(required=true, value="memberTypeId") String memberTypeId) {
		final Response wsResponse = organizationTypeClient.addChild(typeId, memberTypeId);
		final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, false);
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/organizationTypeChildDelete", method=RequestMethod.POST)
	public String organizationTypeChildDelete(final HttpServletRequest request,
									  		  final @RequestParam(required=true, value="typeId") String typeId,
									  		  final @RequestParam(required=true, value="memberTypeId") String memberTypeId) {
		final Response wsResponse = organizationTypeClient.removeChild(typeId, memberTypeId);
		final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, true);
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}

    private BasicAjaxResponse getResponseAfterEntity2EntityAddition(final Response wsResponse, final boolean isDelete) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken((isDelete) ? SuccessMessage.ENTITY_DELETED : SuccessMessage.ENTITY_ADDED));
        } else {
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                case CANT_ADD_YOURSELF_AS_CHILD:
                    ajaxResponse.addError(new ErrorToken(Errors.CANT_MAKE_ENTITY_A_CHILD_OF_ITSELF));
                    break;
                case CIRCULAR_DEPENDENCY:
                    ajaxResponse.addError(new ErrorToken(Errors.CIRCULAR_DEPENDENCY));
                    break;
                case RELATIONSHIP_EXISTS:
                    ajaxResponse.addError(new ErrorToken(Errors.RELATIONSHIP_EXISTS));
                    break;
                case OBJECT_NOT_FOUND:
                    ajaxResponse.addError(new ErrorToken(Errors.OBJECT_DOES_NOT_EXIST));
                    break;
                case INVALID_ARGUMENTS:
                    ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
                    break;
                default:
                    ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
                    break;
                }
            } else {
                ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
            }
        }
        return ajaxResponse;
    }
}
