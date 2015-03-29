package org.openiam.ui.webconsole.web.mvc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.RoleSearchBean;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.loc.dto.Location;
import org.openiam.idm.srvc.meta.domain.MetadataTypeGrouping;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.role.ws.RoleDataWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.ui.rest.api.model.LocationBean;
import org.openiam.ui.util.WSUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.webconsole.validator.UserContactInfoValidator;
import org.openiam.ui.rest.api.model.AddressBean;
import org.openiam.ui.rest.api.model.EmailBean;
import org.openiam.ui.rest.api.model.PhoneBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserContactInfoController extends BaseUserController {


    @Autowired
    private UserContactInfoValidator userContactInfoValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userContactInfoValidator);
    }

    @RequestMapping(value = "/editUserContact", method = RequestMethod.GET)
    public String getEditPage(final HttpServletRequest request, final HttpServletResponse response,
                              final @RequestParam(required = true, value = "id") String userId,
                              @RequestParam(value = "type", required = false) String type) throws IOException {
        if (StringUtils.isBlank(type)) {
            type = "emails";
        }
        final UserSearchBean searchBean = new UserSearchBean();
        searchBean.setKey(userId);
        final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
        if (CollectionUtils.isEmpty(userList)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("User with id '%s' does not exist", userId));
            return null;
        }

        final User user = userList.get(0);
        request.setAttribute("user", user);
        request.setAttribute("type", type);

        MetadataTypeGrouping grouping = EMAIL_GROUPING;
        if ("addresses".equals(type))
            grouping = ADDRESS_GROUPING;
        else if ("phones".equals(type))
            grouping = PHONE_GROUPING;

        request.setAttribute("typeList", jacksonMapper.writeValueAsString(getMetadataTypesByGrouping(grouping)));

        setMenuTree(request, userEditRootMenuId);
        return "users/contactUser";
    }

    @RequestMapping(value = "/getEmailsForUser", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getEmailsForUser(final @RequestParam(required = true, value = "id") String userId, final @RequestParam(required = true,
                                                                                                                        value = "size") Integer size,
                                  final @RequestParam(required = true, value = "from") Integer from) {
        final List<EmailAddress> emailList = userDataWebService.getEmailAddressListByPage(userId, size, from);
        final Integer count = (from == 0) ? userDataWebService.getNumOfEmailsForUser(userId) : null;
        final List<EmailBean> beanList = new LinkedList<EmailBean>();
        if (CollectionUtils.isNotEmpty(emailList)) {
            for (final EmailAddress email : emailList) {
                beanList.add(new EmailBean(email));
            }
        }
        return new BeanResponse(beanList, count);
    }

    @RequestMapping(value = "/getAddressesForUser", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getAddressesForUser(final @RequestParam(required = true, value = "id") String userId,
                                     final @RequestParam(required = true, value = "size") Integer size,
                                     final @RequestParam(required = true, value = "from") Integer from) {
        final List<Address> addressList = userDataWebService.getAddressListByPage(userId, size, from);
        final Integer count = (from == 0) ? userDataWebService.getNumOfAddressesForUser(userId) : null;
        final List<AddressBean> beanList = new LinkedList<AddressBean>();
        if (CollectionUtils.isNotEmpty(addressList)) {
            for (final Address address : addressList) {
                beanList.add(new AddressBean(address));
            }
        }
        return new BeanResponse(beanList, count);
    }


    @RequestMapping(value = "/getLocationsForUser", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getLocationsForUser(final @RequestParam(required = true, value = "id") String userId,
                                    final @RequestParam(required = true, value = "from") Integer from,
                                    final @RequestParam(required = true, value = "size") Integer size) {
        List<Location> locations = organizationDataService.getLocationListByPageForUser(userId, from, size);

        final List<LocationBean> beanList = new LinkedList<LocationBean>();
        int cnt = 0;
        if (locations != null && locations.size() > 0) {
            cnt = locations.size();
            for (final Location locationEl : locations) {
                beanList.add(new LocationBean(locationEl));
            }
        }

        return new BeanResponse(beanList, cnt);
    }

    @RequestMapping(value = "/getPhonesForUser", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getPhonesForUser(final @RequestParam(required = true, value = "id") String userId, final @RequestParam(required = true,
                                                                                                                        value = "size") Integer size,
                                  final @RequestParam(required = true, value = "from") Integer from) {
        final List<Phone> phoneList = userDataWebService.getPhoneListByPage(userId, size, from);
        final Integer count = (from == 0) ? userDataWebService.getNumOfPhonesForUser(userId) : null;
        final List<PhoneBean> beanList = new LinkedList<PhoneBean>();
        if (CollectionUtils.isNotEmpty(phoneList)) {
            for (final Phone phone : phoneList) {
                beanList.add(new PhoneBean(phone));
            }
        }
        return new BeanResponse(beanList, count);
    }

    @RequestMapping(value = "/copyLocationToAddress", method = RequestMethod.POST)
    public String copyLocationToAddress(final HttpServletRequest request,
                                        final @RequestParam(required = true, value = "locationId") String locationId,
                                        final @RequestParam(required = true, value = "userId") String userId) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        ErrorToken errorToken = null;
        SuccessToken successToken = null;

        try {
            Location location = organizationDataService.getLocationById(locationId);
            SuccessMessage successMessage = SuccessMessage.USER_ADDRESS_SAVED;

            Response wsResponse = null;
            if (location != null) {
                Address newAddress = locationToAddress(location);
                newAddress.setParentId(userId);
                newAddress.setMetadataTypeId("OFFICE_ADDRESS");
                wsResponse = userDataWebService.addAddress(newAddress);
            }

            WSUtils.setWSClientTimeout(provisionService, 360000L);


            if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                successToken = new SuccessToken(successMessage);
                RoleSearchBean rsb = new RoleSearchBean();
                rsb.setName(location.getName());
                List<Role> roles = roleServiceClient.findBeans(rsb, null, 0, Integer.MAX_VALUE);
                if ((roles != null) && (roles.size() > 0)) {
                    roleServiceClient.addUserToRole(roles.get(0).getId(), userId, null);
                }

            } else {
                errorToken = new ErrorToken(Errors.CANNOT_SAVE_USER_ADDRESS);
                if (wsResponse.getErrorCode() != null) {
                    switch (wsResponse.getErrorCode()) {
                        case ADDRESS_TYPE_DUPLICATED:
                            errorToken = new ErrorToken(Errors.ADDRESS_TYPE_DUPLICATED);
                            break;
                        case ADDRESS_TYPE_REQUIRED:
                            errorToken = new ErrorToken(Errors.ADDRESS_TYPE_REQUIRED);
                            break;
                        default:
                            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
                            break;
                    }
                }
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

    @RequestMapping(value = "/saveOrRemoveUserAddress", method = RequestMethod.POST)
    public String saveOrRemoveUserAddress(final HttpServletRequest request, @RequestBody @Valid AddressBean address) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        ErrorToken errorToken = null;
        SuccessToken successToken = null;

        try {
            Address addressDto = convertAddressToDTO(address);
            SuccessMessage successMessage = SuccessMessage.USER_ADDRESS_SAVED;

            Response wsResponse = null;
            if (address.getOperation() == AttributeOperationEnum.DELETE) {
                successMessage = SuccessMessage.USER_ADDRESS_DELETED;
            }
            if (!provisionServiceFlag) {
                if (address.getOperation() == AttributeOperationEnum.DELETE) {
                    wsResponse = userDataWebService.removeAddress(address.getId());
                } else if (address.getId() == null) {
                    wsResponse = userDataWebService.addAddress(addressDto);
                } else {
                    wsResponse = userDataWebService.updateAddress(addressDto);
                }
            } else {
                User user = userDataWebService.getUserWithDependent(address.getUserId(), cookieProvider.getUserId(request), true);
                ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));
                Address addr = StringUtils.isNotBlank(addressDto.getAddressId()) ?
                        userDataWebService.getAddressById(addressDto.getAddressId()) : null;
                if (address.getOperation() == AttributeOperationEnum.DELETE) {
                    if (addr != null) {
                        for (Address a : pUser.getAddresses()) {
                            if (addr.getAddressId().equals(a.getAddressId())) {
                                a.setOperation(AttributeOperationEnum.DELETE);
                                break;
                            }
                        }
                    }
                } else {
                    if (addr != null) {
                        for (Address a : pUser.getAddresses()) {
                            if (addr.getAddressId().equals(a.getAddressId())) {
                                pUser.getAddresses().remove(a);
                                addressDto.setOperation(AttributeOperationEnum.REPLACE);
                                pUser.getAddresses().add(addressDto);
                                break;
                            }
                        }
                    } else {
                        addressDto.setOperation(AttributeOperationEnum.ADD);
                        pUser.getAddresses().add(addressDto);
                    }
                }
                WSUtils.setWSClientTimeout(provisionService, 360000L);
                wsResponse = provisionService.modifyUser(pUser);
            }

            if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                successToken = new SuccessToken(successMessage);

            } else {
                errorToken = new ErrorToken(Errors.CANNOT_SAVE_USER_ADDRESS);
                if (wsResponse.getErrorCode() != null) {
                    switch (wsResponse.getErrorCode()) {
                        case ADDRESS_TYPE_DUPLICATED:
                            errorToken = new ErrorToken(Errors.ADDRESS_TYPE_DUPLICATED);
                            break;
                        case ADDRESS_TYPE_REQUIRED:
                            errorToken = new ErrorToken(Errors.ADDRESS_TYPE_REQUIRED);
                            break;
                        default:
                            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
                            break;
                    }
                }
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

    @RequestMapping(value = "/saveOrRemoveUserPhone", method = RequestMethod.POST)
    public String saveOrRemoveUserPhone(final HttpServletRequest request, @RequestBody @Valid PhoneBean phone) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        ErrorToken errorToken = null;
        SuccessToken successToken = null;

        try {
            Phone phoneDto = convertPhoneToDTO(phone);
            SuccessMessage successMessage = SuccessMessage.USER_PHONE_SAVED;

            Response wsResponse = null;
            if (phone.getOperation() == AttributeOperationEnum.DELETE) {
                successMessage = SuccessMessage.USER_PHONE_DELETED;
            }
            if (!provisionServiceFlag) {
                if (phone.getOperation() == AttributeOperationEnum.DELETE) {
                    wsResponse = userDataWebService.removePhone(phone.getId());
                } else if (phone.getId() == null) {
                    wsResponse = userDataWebService.addPhone(phoneDto);
                } else {
                    wsResponse = userDataWebService.updatePhone(phoneDto);
                }
            } else {
                User user = userDataWebService.getUserWithDependent(phone.getUserId(), cookieProvider.getUserId(request), true);
                ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));
                Phone ph = StringUtils.isNotBlank(phoneDto.getPhoneId()) ?
                        userDataWebService.getPhoneById(phoneDto.getPhoneId()) : null;
                if (phone.getOperation() == AttributeOperationEnum.DELETE) {
                    if (ph != null) {
                        for (Phone p : pUser.getPhones()) {
                            if (ph.getPhoneId().equals(p.getPhoneId())) {
                                p.setOperation(AttributeOperationEnum.DELETE);
                                break;
                            }
                        }
                    }
                } else {
                    if (ph != null) {
                        for (Phone p : pUser.getPhones()) {
                            if (ph.getPhoneId().equals(p.getPhoneId())) {
                                pUser.getPhones().remove(p);
                                phoneDto.setOperation(AttributeOperationEnum.REPLACE);
                                pUser.getPhones().add(phoneDto);
                                break;
                            }
                        }
                    } else {
                        phoneDto.setOperation(AttributeOperationEnum.ADD);
                        pUser.getPhones().add(phoneDto);
                    }
                }
                WSUtils.setWSClientTimeout(provisionService, 360000L);
                wsResponse = provisionService.modifyUser(pUser);
            }

            if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                successToken = new SuccessToken(successMessage);

            } else {
                errorToken = new ErrorToken(Errors.CANNOT_SAVE_USER_PHONE);
                if (wsResponse.getErrorCode() != null) {
                    switch (wsResponse.getErrorCode()) {
                        case PHONE_TYPE_DUPLICATED:
                            errorToken = new ErrorToken(Errors.PHONE_TYPE_DUPLICATED);
                            break;
                        case PHONE_TYPE_REQUIRED:
                            errorToken = new ErrorToken(Errors.PHONE_TYPE_REQUIRED);
                            break;
                        default:
                            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
                            break;
                    }
                }
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

    @RequestMapping(value = "/saveOrRemoveUserEmail", method = RequestMethod.POST)
    public String saveOrRemoveUserEmail(final HttpServletRequest request, @RequestBody @Valid EmailBean email) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        ErrorToken errorToken = null;
        SuccessToken successToken = null;

        try {
            EmailAddress emailDto = convertEmailToDTO(email);
            SuccessMessage successMessage = SuccessMessage.USER_EMAIL_SAVED;

            Response wsResponse = null;
            if (email.getOperation() == AttributeOperationEnum.DELETE) {
                successMessage = SuccessMessage.USER_EMAIL_DELETED;
            }
            if (!provisionServiceFlag) {
                if (email.getOperation() == AttributeOperationEnum.DELETE) {
                    wsResponse = userDataWebService.removeEmailAddress(email.getId());
                } else if (email.getId() == null) {
                    wsResponse = userDataWebService.addEmailAddress(emailDto);
                } else {
                    wsResponse = userDataWebService.updateEmailAddress(emailDto);
                }
            } else {
                User user = userDataWebService.getUserWithDependent(email.getUserId(), cookieProvider.getUserId(request), true);
                ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));
                EmailAddress em = StringUtils.isNotBlank(emailDto.getEmailId()) ?
                        userDataWebService.getEmailAddressById(emailDto.getEmailId()) : null;
                if (email.getOperation() == AttributeOperationEnum.DELETE) {
                    if (em != null) {
                        for (EmailAddress e : pUser.getEmailAddresses()) {
                            if (em.getEmailId().equals(e.getEmailId())) {
                                e.setOperation(AttributeOperationEnum.DELETE);
                                break;
                            }
                        }
                    }
                } else {
                    if (em != null) {
                        for (EmailAddress e : pUser.getEmailAddresses()) {
                            if (em.getEmailId().equals(e.getEmailId())) {
                                pUser.getEmailAddresses().remove(e);
                                emailDto.setOperation(AttributeOperationEnum.REPLACE);
                                pUser.getEmailAddresses().add(emailDto);
                                break;
                            }
                        }
                    } else {
                        emailDto.setOperation(AttributeOperationEnum.ADD);
                        pUser.getEmailAddresses().add(emailDto);
                    }
                }
                WSUtils.setWSClientTimeout(provisionService, 360000L);
                wsResponse = provisionService.modifyUser(pUser);
            }

            if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                successToken = new SuccessToken(successMessage);

            } else {
                errorToken = new ErrorToken(Errors.CANNOT_SAVE_USER_EMAIL);
                if (wsResponse.getErrorCode() != null) {
                    switch (wsResponse.getErrorCode()) {
                        case EMAIL_ADDRESS_TYPE_DUPLICATED:
                            errorToken = new ErrorToken(Errors.EMAIL_ADDRESS_TYPE_DUPLICATED);
                            break;
                        case EMAIL_ADDRESS_TYPE_REQUIRED:
                            errorToken = new ErrorToken(Errors.EMAIL_TYPE_REQUIRED);
                            break;
                        default:
                            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
                            break;
                    }
                }
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

    private EmailAddress convertEmailToDTO(EmailBean email) {
        EmailAddress result = new EmailAddress();

        result.setOperation(email.getOperation());
        result.setEmailId(email.getId());
        result.setEmailAddress(email.getEmail());
        result.setTypeDescription(email.getType());
        result.setMetadataTypeId(email.getTypeId());
        result.setDescription(email.getDescription());
        result.setParentId(email.getUserId());
        result.setIsActive(email.getActive());
        result.setIsDefault(email.getDefault());

        return result;
    }

    private Address convertAddressToDTO(AddressBean address) {
        Address result = new Address();

        result.setCountry(address.getCountry());
        result.setOperation(address.getOperation());
        result.setAddressId(address.getId());
        result.setIsActive(address.isActive());
        result.setIsDefault(address.isDefault());
        result.setBldgNumber(address.getBldgNumber());
        result.setAddress1(address.getAddress1());
        result.setAddress2(address.getAddress2());
        result.setCity(address.getCity());
        result.setPostalCd(address.getPostalCd());
        result.setState(address.getState());
        result.setTypeDescription(address.getType());
        result.setMetadataTypeId(address.getTypeId());
        result.setParentId(address.getUserId());

        return result;
    }

    private Phone convertPhoneToDTO(PhoneBean phone) {
        Phone result = new Phone();
        result.setCountryCd(phone.getCountryCd());
        result.setOperation(phone.getOperation());
        result.setPhoneId(phone.getId());
        result.setIsActive(phone.isActive());
        result.setIsDefault(phone.isDefault());
        result.setTypeDescription(phone.getType());
        result.setMetadataTypeId(phone.getTypeId());
        result.setAreaCd(phone.getAreaCd());
        result.setPhoneExt(phone.getPhoneExt());
        result.setPhoneNbr(phone.getPhoneNbr());
        result.setParentId(phone.getUserId());
        result.setParentType("USER");

        return result;
    }

    private Address locationToAddress(Location location) {
        Address result = new Address();
        result.setBldgNumber(location.getBldgNum());
        result.setCountry(location.getCountry());
        result.setCity(location.getCity());
        result.setState(location.getState());
        result.setDescription(location.getDescription());
        result.setStreetDirection(location.getStreetDirection());
        result.setAddress1(location.getAddress1());
        result.setAddress2(location.getAddress2());
        result.setAddress3(location.getAddress3());
        result.setPostalCd(location.getPostalCd());
        result.setName(location.getName());
        result.setLocationId(location.getLocationId());

        return result;
    }

}
