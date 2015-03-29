package org.openiam.ui.web.mvc.entitlements;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openiam.base.ws.Response;
import org.openiam.idm.searchbeans.OrganizationTypeSearchBean;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.dto.OrganizationAttribute;
import org.openiam.idm.srvc.org.dto.OrganizationType;
import org.openiam.idm.srvc.user.dto.UserAttribute;
import org.openiam.idm.srvc.user.util.DelegationFilterHelper;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.rest.api.model.OrganizationModel;
import org.openiam.ui.util.ErrorUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public abstract class AbstractOrganizationController extends AbstractEntityEntitlementsController<Organization> {
	
    @RequestMapping(value="/organizations")
    public String find(final HttpServletRequest request,
    				   final HttpServletResponse response) throws Exception {
        final List<KeyNameBean> organizationTypes = getOrgTypeListAsKeyNameBeans();
        setMenuTree(request, getRootMenu());
        request.setAttribute("organizationTypes", (organizationTypes != null) ? jacksonMapper.writeValueAsString(organizationTypes) : null);
        return "jar:organization/organizations";
    }
    
    @RequestMapping(value="/editOrganization", method=RequestMethod.GET)
	public String editOrganization(final HttpServletRequest request,
						   		   final HttpServletResponse response,
						   		   final @RequestParam(required=false,value="id") String id) throws IOException {
		Organization organization = new Organization();
        String requesterId = this.getRequesterId(request);
		if(StringUtils.isNotBlank(id)) {
			organization = organizationDataService.getOrganizationLocalized(id, requesterId, getCurrentLanguage());
			if(organization == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
		}
        Map<String, UserAttribute> reqAttrib = getUserAttributesAsMap(requesterId);//userDataWebService.getUserAttributesAsMap(requesterId);

		request.setAttribute("organization", organization);



        request.setAttribute("organizationTypes", getAllowedOrgTypeListAsKeyNameBeans(requesterId));
        request.setAttribute("isDelegationFilterSet", DelegationFilterHelper.isOrgFilterSet(reqAttrib)
                                                      || DelegationFilterHelper.isDeptFilterSet(reqAttrib)
                                                      || DelegationFilterHelper.isDivisionFilterSet(reqAttrib));
		request.setAttribute("isNew", StringUtils.isBlank(id));
		request.setAttribute("organizationAsJSON", jacksonMapper.writeValueAsString(this.toModel(organization)));
		if(StringUtils.isNotBlank(id)) {
			setMenuTree(request, getEditMenu());
		} else {
			setMenuTree(request, getRootMenu());
		}
		return "jar:organization/editOrganization";
	}

    @RequestMapping(value = "/getAllowedParentOrganizationTypes", method = RequestMethod.POST)
    public @ResponseBody BeanResponse getAllowedParentOrganisations(final HttpServletRequest request,
                                                                    @RequestParam(value = "orgTypeId", required = true) String orgTypeId) throws IOException {
        String requesterId = this.getRequesterId(request);
        final List<OrganizationType> organizationTypes = organizationTypeClient.getAllowedParents(orgTypeId, requesterId, getCurrentLanguage());
        return new BeanResponse(mapper.mapToList(organizationTypes, KeyNameBean.class), organizationTypes.size());
    }

    @RequestMapping(value="/editOrganization", method=RequestMethod.POST)
	public String saveOrganization(final HttpServletRequest request,
								   final HttpServletResponse response,
						   		   @RequestBody final OrganizationModel org) throws Exception {

        Organization organization = this.toDto(org);
		final BasicAjaxResponse ajaxResponse = doEdit(request, response, organization);
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}

	@RequestMapping(value="/deleteOrganization", method=RequestMethod.POST)
	public String deleteOrganization(final HttpServletRequest request,
									 final HttpServletResponse response,
									 final @RequestParam(value="id", required=true) String id) throws Exception {
		final Organization entity = getEntity(id, request);
		final BasicAjaxResponse ajaxResponse = doDelete(request, response, entity);
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}

    private OrganizationModel toModel(Organization org) {
        OrganizationModel model = new OrganizationModel();
        model.setId(org.getId());
        model.setDescription(org.getDescription());
        model.setName(org.getName());
        model.setOrganizationTypeId(org.getOrganizationTypeId());
        model.setAbbreviation(org.getAbbreviation());
        model.setDomainName(org.getDomainName());
        model.setInternalOrgId(org.getInternalOrgId());
        model.setLdapStr(org.getLdapStr());
        model.setMdTypeId(org.getMdTypeId());
        model.setMetadataTypeName(org.getMetadataTypeName());
        model.setSelectable(org.isSelectable());
        model.setSymbol(org.getSymbol());
        model.setAlias(org.getAlias());

        if(CollectionUtils.isNotEmpty(org.getAttributes())){
            model.setAttributes(new ArrayList<OrganizationAttribute>(org.getAttributes()));

            Collections.sort(model.getAttributes(), new Comparator<OrganizationAttribute>() {
                @Override
                public int compare(OrganizationAttribute o1, OrganizationAttribute o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
        }
        return model;
    }

    private Organization toDto(OrganizationModel model){
        Organization dto = new Organization();
        dto.setId(model.getId());
        dto.setDescription(model.getDescription());
        dto.setName(model.getName());
        dto.setOrganizationTypeId(model.getOrganizationTypeId());
        dto.setAbbreviation(model.getAbbreviation());
        dto.setDomainName(model.getDomainName());
        dto.setInternalOrgId(model.getInternalOrgId());
        dto.setLdapStr(model.getLdapStr());
        dto.setMdTypeId(model.getMdTypeId());
        dto.setSelectable(model.getSelectable());
        dto.setSymbol(model.getSymbol());
        dto.setAlias(model.getAlias());

        if(CollectionUtils.isNotEmpty(model.getAttributes())){
            dto.setAttributes(new HashSet<OrganizationAttribute>(model.getAttributes()));
        }
        return dto;
    }



    @RequestMapping(value = "organization/onExpand", method = RequestMethod.POST)
    public @ResponseBody
    BeanResponse expand(final HttpServletRequest request, @RequestBody String id)
            throws JsonGenerationException, JsonMappingException, IOException {
        List<Organization> orgs = organizationDataService.getChildOrganizationsLocalized(id, this.getRequesterId(request), -1, -1, getCurrentLanguage());
        Organization organization = organizationDataService.getOrganizationLocalized(id,this.getRequesterId(request), getCurrentLanguage());
        List<OrganizationModel> resultList = new ArrayList<OrganizationModel>();
        if (!CollectionUtils.isEmpty(orgs)) {
//            for (Organization org : orgs) {
//                resultList.add(this.toModel(org, organization.getName(),
//                        this.getRequesterId(request)));
//            }
        }
        return new BeanResponse(resultList, resultList.size());
    }
    
    @Override
    public Organization getEntity(final String id, final HttpServletRequest request) {
    	return organizationDataService.getOrganizationLocalized(id, getRequesterId(request), getCurrentLanguage());
    }

	@Override
	protected List<ErrorToken> getEditErrors(final Response wsResponse, final HttpServletRequest request, final Organization entity) {
		final List<ErrorToken> errorList = new LinkedList<ErrorToken>();
		if(wsResponse != null) {
			if(wsResponse.isFailure()) {
				if(wsResponse.getErrorCode() == null) {
					errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
				} else {
					switch(wsResponse.getErrorCode()) {
						case ORGANIZATION_NAME_NOT_SET:
							errorList.add(new ErrorToken(Errors.INVALID_ORGANIZTION_NAME));
							break;
						case NAME_TAKEN:
							errorList.add(new ErrorToken(Errors.ORGANIZATION_NAME_TAKEN));
							break;
						case ORGANIZATION_TYPE_NOT_SET:
							errorList.add(new ErrorToken(Errors.ORGANIZATION_TYPE_MISSING));
							break;
						case CLASSIFICATION_NOT_SET:
							errorList.add(new ErrorToken(Errors.ORGANIZATION_CLASSIFICATION_MISSING));
							break;
						case VALIDATION_ERROR:
							errorList.addAll(ErrorUtils.getESBErrorTokens(wsResponse));
		                    break;
                        case FAIL_PREPROCESSOR:
                            errorList.add(new ErrorToken(Errors.ORG_FAIL_PREPROCESSOR));
                            break;
                        case FAIL_POSTPROCESSOR:
                            errorList.add(new ErrorToken(Errors.ORG_FAIL_POSTPROCESSOR));
                            break;
						default:
							errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
							break;
					}
				}
			}
		} else {
			errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
		}
		return errorList;
	}

    protected List<KeyNameBean> getAllowedOrgTypeListAsKeyNameBeans(String requesterId) {
        final OrganizationTypeSearchBean organizationTypeSearchBean = new OrganizationTypeSearchBean();
        organizationTypeSearchBean.setDeepCopy(false);
        final List<OrganizationType> organizationTypes = organizationTypeClient.findAllowedChildrenByDelegationFilterLocalized(requesterId, getCurrentLanguage());
        return mapper.mapToList(organizationTypes, KeyNameBean.class);
    }

    private Map<String,UserAttribute> getUserAttributesAsMap(String userId){
        List<UserAttribute> userAttributes = userDataWebService.getUserAttributesInternationalized(userId, getCurrentLanguage());
        Map<String,UserAttribute> result = new HashMap<String, UserAttribute>();
        if (userAttributes != null && !userAttributes.isEmpty()) {
            for (UserAttribute entity : userAttributes) {
                result.put(entity.getName(), entity);
            }
        }
        return result;
    }

    private List<OrganizationModel> convertToModel(List<Organization> orgs, String requesterId){
        List<OrganizationModel> resultList = new ArrayList<OrganizationModel>();
        if (!CollectionUtils.isEmpty(orgs)) {
//            for (Organization org : orgs) {
//                resultList.add(this.toModel(org, org.getName(), requesterId));
//            }
        }
        return resultList;
    }
}
