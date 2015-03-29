package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.ws.*;

import org.openiam.idm.srvc.loc.dto.Location;
import org.openiam.idm.srvc.org.dto.Organization;

import org.openiam.ui.rest.api.model.LocationBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;

import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractOrganizationController;
import org.openiam.ui.web.validator.UserInfoValidator;
import org.openiam.ui.webconsole.validator.LocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Controller
public class OrganizationController extends AbstractOrganizationController {

    @Autowired
    private LocationValidator locationValidator;

	@Value("${org.openiam.ui.organization.root.menu.id}")
	private String organizationRootMenuName;
	
	@Value("${org.openiam.ui.organization.edit.menu.id}")
	private String organizationEditMenuName;

	@Override
	protected String getRootMenu() {
		return organizationRootMenuName;
	}

	@Override
	protected String getEditMenu() {
		return organizationEditMenuName;
	}

	@RequestMapping(value="/organizationMembership", method=RequestMethod.GET)
	public String editOrganization(final HttpServletRequest request,
						   		   final HttpServletResponse response,
						   		   final @RequestParam(required=true,value="id") String organizationId,
						   		   @RequestParam(required=false,value="type") String type) throws IOException {
			
		final Organization organization = organizationDataService.getOrganizationLocalized(organizationId, this.getRequesterId(request), getCurrentLanguage());
		if(organization == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Organization with ID: '%s' not found", organizationId));
			return null;
		}
		
		if(type == null) {
			type = "childorganizations";
		}
		
		request.setAttribute("type", type);
		request.setAttribute("organization", organization);
		setMenuTree(request, organizationEditMenuName);
		return "organization/organizationMembership";
	}

    @RequestMapping(value="/organizationLocation", method=RequestMethod.GET)
    public String editOrganizationLocation(final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   final @RequestParam(required=true,value="id") String organizationId,
                                   @RequestParam(required=false,value="type") String type) throws IOException {

        final Organization organization = organizationDataService.getOrganizationLocalized(organizationId, this.getRequesterId(request), getCurrentLanguage());
        if(organization == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Organization with ID: '%s' not found", organizationId));
            return null;
        }


        request.setAttribute("organization", organization);
        setMenuTree(request, organizationEditMenuName);
        return "organization/organizationLocation";
    }

    @RequestMapping(value = "/getLocationForOrganization", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getLocationForOrganization(final @RequestParam(required = true, value = "id") String organizationId,
                                     final @RequestParam(required = true, value = "size") Integer size,
                                     final @RequestParam(required = true, value = "from") Integer from,
                                     final @RequestParam(required = false, value = "sortBy") String sortBy,
                                     final @RequestParam(required = false, value = "orderBy") String orderBy) {

        List<Location> locations = organizationDataService.getLocationListByPage(organizationId, from, size);
        int cnt = organizationDataService.getNumOfLocationsForOrganization(organizationId);

        final List<LocationBean> beanList = new LinkedList<LocationBean>();

        if (locations != null && locations.size() > 0) {
            for (final Location locationEl : locations) {
                 beanList.add(new LocationBean(locationEl));
            }
        }

        return new BeanResponse(beanList, cnt);
    }



    @RequestMapping(value = "/saveOrRemoveOrganizationLocation", method = RequestMethod.POST)
    public String saveOrRemoveOrganizationLocation(final HttpServletRequest request, @RequestBody @Valid LocationBean location) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        ErrorToken errorToken = null;
        SuccessToken successToken = null;

        try {
            Location locationDto = convertLocationToDTO(location);
            SuccessMessage successMessage = SuccessMessage.ORGANIZATION_LOCATION_SAVED;

            Response wsResponse = null;
            if (location.getOperation() == AttributeOperationEnum.DELETE) {
                successMessage = SuccessMessage.ORGANIZATION_LOCATION_DELETED;
            }

            if (location.getOperation() == AttributeOperationEnum.DELETE) {
                wsResponse = organizationDataService.removeLocation(location.getId());
            } else if (location.getId() == null) {
                wsResponse = organizationDataService.addLocation(locationDto);
            } else {
                wsResponse = organizationDataService.updateLocation(locationDto);
            }


            if (wsResponse.getStatus() == org.openiam.base.ws.ResponseStatus.SUCCESS) {
                successToken = new SuccessToken(successMessage);

            } else {
                errorToken = new ErrorToken(Errors.CANNOT_SAVE_LOCATION);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);

        } finally {
            if (errorToken != null) {
                ajaxResponse.setStatus(500);
                ajaxResponse.addError(errorToken);
            } else {
                ajaxResponse.setSuccessToken(successToken);
                ajaxResponse.setStatus(200);
            }
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    private Location convertLocationToDTO(LocationBean location) {
        Location result = new Location();

        result.setLocationId(location.getId());
        result.setName(location.getName());
        result.setDescription(location.getDescription());
        result.setCountry(location.getCountry());
        result.setBldgNum(location.getBldgNum());
        result.setStreetDirection(location.getStreetDirection());
        result.setAddress1(location.getAddress1());
        result.setAddress2(location.getAddress2());
        result.setAddress3(location.getAddress3());
        result.setCity(location.getCity());
        result.setState(location.getState());
        result.setPostalCd(location.getPostalCd());
        result.setOrganizationId(location.getOrganizationId());
        result.setIsActive(location.isActive());

        return result;
    }


	@Override
	protected BasicAjaxResponse doEdit(HttpServletRequest request,
			HttpServletResponse response, Organization organization) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

		final Response wsResponse = organizationDataService.saveOrganization(organization, getRequesterId(request));
		if(wsResponse.isSuccess()) {
            String orgId = (String) wsResponse.getResponseValue();

			ajaxResponse.setStatus(200);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.ORGANIZTION_SAVED));
			if(StringUtils.isBlank(organization.getId())) {
				ajaxResponse.setRedirectURL(new StringBuilder("editOrganization.html?id=").append(wsResponse.getResponseValue()).toString());
			}
		} else {
			ajaxResponse.setErrorList(getEditErrors(wsResponse, request, organization));
			ajaxResponse.setStatus(500);
		}
		return ajaxResponse;
	}

	@Override
	protected BasicAjaxResponse doDelete(HttpServletRequest request,
			HttpServletResponse response, Organization entity) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final String callerId = getRequesterId(request);

		final Response wsResponse = organizationDataService.deleteOrganization(entity.getId());
		if(wsResponse.isSuccess()) {
			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL("organizations.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.ORGANIZTION_DELETED));
		} else {
			ajaxResponse.setErrorList(getDeleteErrors(wsResponse, request, entity));
		}
		return ajaxResponse;
	}
}
