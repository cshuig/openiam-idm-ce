import org.openiam.base.AttributeOperationEnum
import org.openiam.base.ws.Response
import org.openiam.dozer.converter.OrganizationDozerConverter
import org.openiam.dozer.converter.RoleDozerConverter
import org.openiam.idm.searchbeans.OrganizationSearchBean
import org.openiam.idm.srvc.auth.dto.Login
import org.openiam.idm.srvc.auth.ws.LoginDataWebService
import org.openiam.idm.srvc.continfo.dto.Address
import org.openiam.idm.srvc.continfo.dto.EmailAddress
import org.openiam.idm.srvc.continfo.dto.Phone
import org.openiam.idm.srvc.org.dto.Organization
import org.openiam.idm.srvc.org.service.OrganizationDataService
import org.openiam.idm.srvc.org.service.OrganizationService
import org.openiam.idm.srvc.role.service.RoleDataService
import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.synch.dto.LineObject
import org.openiam.idm.srvc.synch.service.AbstractTransformScript
import org.openiam.idm.srvc.synch.service.TransformScript
import org.openiam.idm.srvc.user.dto.User
import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.idm.srvc.user.ws.UserDataWebService
import org.openiam.provision.dto.ProvisionUser
import org.springframework.context.ApplicationContext

class CSVSampleTransformationScript extends AbstractTransformScript {

    private ApplicationContext context

    OrganizationDataService orgService
    UserDataWebService userManager
    LoginDataWebService loginDataWebService;

    @Override
    int execute(LineObject rowObj, ProvisionUser pUser) {
        if (orgService == null) {
            orgService = context?.getBean("orgManager") as OrganizationDataService;
        }
        if (userManager == null) {
            userManager = context.getBean("userWS") as UserDataWebService
        }
        if (loginDataWebService == null) {
            loginDataWebService = context.getBean("loginWS") as LoginDataWebService
        }
        println "** - Transformation script called."
        try {
            populateObject(rowObj, pUser)
        }catch(Exception ex) {
            ex.printStackTrace();
            println "** - Transformation script error."
            return -1;
        }
        println "** - Transformation script completed."

        pUser.setSkipPreprocessor(false)
        pUser.setSkipPostProcessor(false)

        return TransformScript.NO_DELETE
    }

    private void populateObject(LineObject rowObj, ProvisionUser pUser) {

        def columnMap =  rowObj.columnMap
        if (isNewUser) {
            pUser.id = null
        }
        pUser.employeeId = columnMap.get("EMPLOYEE_ID")?.value
        if (pUser.employeeId.length() > 15) {
            pUser.employeeId = pUser.employeeId.substring(0,15)
        }
        pUser.firstName = columnMap.get("FIRST_NAME")?.value
        pUser.lastName = columnMap.get("LAST_NAME")?.value
        pUser.title = columnMap.get("TITLE")?.value
        pUser.status = (columnMap.get("STATUS") != null && org.apache.commons.lang.StringUtils.isNotEmpty(columnMap.get("STATUS").value)) ? UserStatusEnum.getFromString(columnMap.get("STATUS").value) : UserStatusEnum.ACTIVE;

        if (isNewUser) {
            // Processing email
            def email = new EmailAddress()
            try {
                email.name = "PRIMARY_EMAIL"
                email.metadataTypeId = "PRIMARY_EMAIL"

                email.emailAddress = columnMap.get("EMAIL")?.value
                addEmailAddress(pUser, email);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (isNewUser) {
            try {
                // Processing phone
                def phone = new Phone()
                //CELL_PHONE
                phone.name = "CELL PHONE"
                phone.countryCd = ''
                phone.areaCd = ''
                phone.phoneNbr = columnMap.get("PHONE")?.value
                phone.metadataTypeId = "CELL_PHONE"
                addPhone(pUser, phone)
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (isNewUser) {
            try {
                // Processing address
                def address = new Address()
                address.name = "PRIMARY_LOCATION"
                address.address1 = columnMap.get("ADDRESS")?.value
                address.city = columnMap.get("CITY")?.value
                address.postalCd = columnMap.get("ZIP")?.value
                address.state = columnMap.get("STATE")?.value
                address.metadataTypeId = "PRIMARY_LOCATION"
                addAddress(pUser, address)
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        pUser.mdTypeId = "DEFAULT_USER"

        def attrVal = columnMap.get("ROLE")
        if (attrVal) {
            addRole(pUser, attrVal.value)
        }


        if (columnMap.get("COMPANY") != null && org.apache.commons.lang.StringUtils.isNotEmpty(columnMap.get("COMPANY").value)){
            try {
                addOrganization(pUser, columnMap.get("COMPANY")?.value)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        def supervisor = columnMap.get("SUPERVISOR")?.value
        List<User> supervisorEntityList = pUser.id != null ? userManager.getSuperiors(pUser.id, 0, 100) : new LinkedList<User>();
        if (supervisor) {
            String supervisorPrincipal = supervisor.replaceAll(" ",".");
            User supervisorUser = userManager.getUserByPrincipal(supervisorPrincipal, "0", false);
            if (supervisorUser != null) {
                println("Supervisor User found =" + supervisorUser);
                for (User s : supervisorEntityList) {
                    s.operation = AttributeOperationEnum.DELETE
                    pUser.addSuperior(s)

                }
                supervisorUser.operation = AttributeOperationEnum.ADD
                pUser.addSuperior(supervisorUser)
            }

        }

    }

    private String getMailDomain(String schoolName) {

        println("-- Getting the mail domain for this student " + schoolName);
        OrganizationSearchBean searchBean = new OrganizationSearchBean();
        searchBean.setDeepCopy(false);
        searchBean.setName(schoolName);
        List<Organization> orgList = orgService.findBeans(searchBean, null, -1, -1);
        if (orgList != null && orgList.size() > 0) {

            println("School found..." + orgList);

            for ( Organization o: orgList) {
                if ( o.name != null &&  o.name.equals(schoolName) ) {
                    if ( o != null && o.domainName != null) {
                        return "@" + o.domainName;
                    }
                }
            }

            println("Email attribute not found...");
        }
    }


    private addAddress(ProvisionUser pUser, Address address) {
        address.operation = AttributeOperationEnum.ADD
        if (!isNewUser) {
            for (Address e : pUser.addresses) {
                if (e.metadataTypeId.equalsIgnoreCase(address.metadataTypeId)) {
                    e.updateAddress(address)
                    e.operation = AttributeOperationEnum.REPLACE
                    return
                }
            }
        }
        pUser.addresses.add(address)
    }

    private addPhone(ProvisionUser pUser, Phone phone) {
        phone.operation = AttributeOperationEnum.ADD
        if (!isNewUser) {
            for (Phone e : pUser.phones) {
                if (e.metadataTypeId.equalsIgnoreCase(phone.metadataTypeId)) {
                    e.updatePhone(phone)
                    e.operation = AttributeOperationEnum.REPLACE
                    return
                }
            }
        }
        pUser.phones.add(phone)
    }

    private addEmailAddress(ProvisionUser pUser, EmailAddress emailAddress) {
        emailAddress.operation = AttributeOperationEnum.ADD
        if (!isNewUser) {
            for (EmailAddress e : pUser.emailAddresses) {
                if (e.metadataTypeId.equalsIgnoreCase(emailAddress.metadataTypeId)) {
                    e.updateEmailAddress(emailAddress)
                    e.operation = AttributeOperationEnum.REPLACE
                    return
                }
            }
        }
        pUser.emailAddresses.add(emailAddress)
    }

    private addOrganization(ProvisionUser pUser, String orgName) {
        //try to find in OpenIAM and check on exists
        try{
            OrganizationSearchBean searchBean = new OrganizationSearchBean();
            searchBean.setDeepCopy(false);
            searchBean.setName(orgName);
            List<Organization> orgList = orgService.findBeans(searchBean, null, -1, -1);
            boolean exists = orgList != null && orgList.size() > 0
            if (!exists) {
                println("Organization with Name="+orgName+" doesn't exist, creating new...")

                Organization newOrg = new Organization();
                newOrg.setOperation(AttributeOperationEnum.ADD)
                newOrg.setName(orgName)
                newOrg.setOrganizationTypeId("DIVISION")
                newOrg.setDomainName("openiam.org")
                Response resp = orgService.saveOrganization(newOrg, null);
                String newOrgId = resp.getResponseValue();
            }

            def foundOrg = pUser.affiliations.find { o-> o.name == orgName && o.organizationTypeId == 'ORGANIZATION' }
            if (!foundOrg) {
                def organizationService = context?.getBean("organizationService") as OrganizationService
                org.openiam.dozer.converter.OrganizationDozerConverter organizationDozerConverter = context?.getBean("organizationDozerConverter") as OrganizationDozerConverter
                Organization org = organizationDozerConverter?.convertToDTO(organizationService?.getOrganizationByName(orgName, null, null), false)

                if (org) {
                    org.operation = AttributeOperationEnum.ADD
                    pUser.affiliations.add(org)
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    def addRole(ProvisionUser pUser, String roleName) {
        def foundRole = pUser.roles.find { r-> r.name == roleName }
        if (!foundRole) {
            def roleDataService = context?.getBean("roleDataService") as RoleDataService
            def roleDozerConverter = context?.getBean("roleDozerConverter") as RoleDozerConverter
            def role = roleDozerConverter?.convertToDTO(roleDataService?.getRoleByName(roleName, null), false)
            if (role) {
                role.operation = AttributeOperationEnum.ADD
                pUser.roles.add(role)
            }
        }
    }


    def addAttribute(ProvisionUser pUser, Attribute attr) {
        def userAttr = new UserAttribute(attr.name, attr.value)
        if (!pUser.userAttributes.containsKey(attr.name)) {
            userAttr.operation = AttributeOperationEnum.ADD
        } else {
            if (userAttr.value != attr.value) {
                userAttr.operation = AttributeOperationEnum.REPLACE
            }
        }
        pUser.userAttributes.put(attr.name, userAttr)
    }

    @Override
    void init() {}

}