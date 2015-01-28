import org.openiam.base.AttributeOperationEnum
import org.openiam.dozer.converter.OrganizationDozerConverter
import org.openiam.dozer.converter.RoleDozerConverter
import org.openiam.idm.searchbeans.OrganizationSearchBean
import org.openiam.idm.srvc.continfo.dto.Address
import org.openiam.idm.srvc.org.service.OrganizationService
import org.openiam.idm.srvc.role.service.RoleDataService

import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.synch.dto.LineObject
import org.openiam.idm.srvc.synch.service.AbstractTransformScript
import org.openiam.idm.srvc.synch.service.TransformScript
import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.provision.dto.ProvisionUser
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.idm.srvc.auth.dto.Login
import org.openiam.idm.srvc.continfo.dto.Phone
import org.openiam.idm.srvc.continfo.dto.EmailAddress


public class TransformLDAPActiveDirRecord extends AbstractTransformScript {

    @Override
    void init() {
        println("Transformation script = TransformActiveDirRecord initialization");
    }

    /* constants - maps to a managed sys id*/
    String DOMAIN = "USR_SEC_DOMAIN"

    String LDAP_DEV2 = "101"
    String OPEN_LDAP = "8a8f8890437c55ef01437dc115450083"

    String defaultRole = "End User"
    boolean assignDefaultRole = false

    boolean KEEP_AD_ID = true

    String IDENTITY_ATTRIBUTE = "uid"

    //String IDENTITY_ATTRIBUTE = "userPrincipalName"
    //String IDENTITY_ATTRIBUTE = "distinguishedName"

    public int execute(LineObject rowObj, ProvisionUser pUser) {

        println("Is New User: " + isNewUser)
//     return -1;   
        if (!isNewUser) {
            println("User Object:" + user)
            println("PrincipalList: " + principalList)
            println("User Roles:" + userRoleList)
        } else {
            pUser.id = null
        }


        println("User Pre-Population...")
        populateObject(rowObj, pUser)
        println("User Post-Population...")

        pUser.setStatus(UserStatusEnum.ACTIVE)
        pUser.mdTypeId="DEFAULT_USER"

        pUser.setSkipPreprocessor(false);
        pUser.setSkipPostProcessor(false);

        // Add default role
        if (assignDefaultRole) {
            addRole(pUser, defaultRole)
        }
        return TransformScript.NO_DELETE
    }

    private void populateObject(LineObject rowObj, ProvisionUser pUser) {

        def attrVal
        Map<String, Attribute> columnMap = rowObj.columnMap

        attrVal = columnMap.get(IDENTITY_ATTRIBUTE)
        if (attrVal?.value) {
            addAttribute(pUser, attrVal)
        }

        /*      attrVal = columnMap.get("company")
              if (attrVal?.value) {
                  String orgName = attrVal.value
                  if (orgName) {
                      addOrganization(pUser, orgName, "ORGANIZATION")
                  }
              }

              attrVal = columnMap.get("department")
              if (attrVal) {
                  String orgName = attrVal.value
                  if (orgName) {
                      addOrganization(pUser, orgName, "DEPARTMENT")
                  }
              }
      */


        attrVal = columnMap.get("givenName")
        if (attrVal?.value) {
            pUser.firstName = attrVal.value
        }

        attrVal = columnMap.get("sn")
        if (attrVal?.value) {
            pUser.lastName = attrVal.value
        }

        attrVal = columnMap.get("title")

        if (attrVal?.value) {
            pUser.title = attrVal.value.length() <=30 ? attrVal.value : attrVal.value.substring(0,30)
        }

        attrVal = columnMap.get("employeeID")
        if (attrVal?.value) {
            pUser.employeeId = attrVal.value
        }

        attrVal = columnMap.get("ou")
        if (attrVal?.value) {
            addAttribute(pUser, attrVal)
        }

        attrVal = columnMap.get("mail")
        if (attrVal) {
            addAttribute(pUser, attrVal)

            // Processing email address
            def emailAddress = new EmailAddress()
            emailAddress.name = "PRIMARY_EMAIL"
            emailAddress.isDefault = true
            emailAddress.isActive = true
            emailAddress.emailAddress = attrVal.value
            emailAddress.metadataTypeId = "PRIMARY_EMAIL"
            addEmailAddress(pUser, emailAddress)

        }	else {
            println("mail attribute was not found")
        }

        attrVal = columnMap.get("street")
        if(attrVal?.value){
            addAttribute(pUser, attrVal)
        }
        attrVal = columnMap.get("st")
        if(attrVal?.value){
            addAttribute(pUser, attrVal)
        }

        // Processing address
/*        def address = new Address()
        address.name = "PRIMARY_LOCATION"
        address.address1 = columnMap.get("street")?.value?: columnMap.get("streetAddress")?.value
        address.city = columnMap.get("l")?.value
        address.postalCd = columnMap.get("postalCode")?.value
        address.state = columnMap.get("st")?.value
        address.metadataTypeId = "PRIMARY_LOCATION"
        addAddress(pUser, address)
*/
        println(" - Processing Phone objects: ")
        attrVal = columnMap.get("mobile")
        if(attrVal?.value){
            addAttribute(pUser, attrVal)
        }


        if (attrVal) {
            def phone = new Phone()
            phone.name = "CELL_PHONE"
            phone.phoneNbr = attrVal.value
            phone.metadataTypeId = "CELL_PHONE"
            addPhone(pUser, phone)
        }

        attrVal = columnMap.get("telephoneNumber")
        attrVal = columnMap.get("mobile")
        if(attrVal?.value){
            addAttribute(pUser, attrVal)
        }


        if (attrVal) {
            def phone = new Phone()
            phone.name = "DESK_PHONE"
            phone.phoneNbr = attrVal.value
            phone.metadataTypeId = "DESK_PHONE"
            addPhone(pUser, phone)
        }

        attrVal = columnMap.get("facsimileTelephoneNumber")
        if(attrVal?.value){
            addAttribute(pUser, attrVal)
        }

        if (attrVal) {
            def phone = new Phone()
            phone.name = "FAX"
            phone.phoneNbr = attrVal.value
            phone.metadataTypeId = "FAX"
            addPhone(pUser, phone)
        }

        if (KEEP_AD_ID && isNewUser) {
            println(" - Processing PrincipalName and DN")
            attrVal = columnMap.get(IDENTITY_ATTRIBUTE)
            if (attrVal) {
                // PRE-POPULATE THE USER LOGIN. IN SOME CASES THE COMPANY WANTS TO KEEP THE LOGIN THAT THEY HAVE
                // THIS SHOWS HOW WE CAN DO THAT
                /*  AD primary identity  */
                def lg = new Login()
                lg.operation = AttributeOperationEnum.ADD
                lg.login = attrVal.value
                lg.managedSysId = "0"
                pUser.principalList.add(lg)

                /*  Oracle LDAP target system identity  */
                Login lg2 = new Login()
                lg2.operation = AttributeOperationEnum.ADD
                lg2.login = attrVal.value
                lg2.managedSysId = LDAP_DEV2
                pUser.principalList.add(lg2)

                /* OpenLDAP target system identity */
                Login lg3 = new Login()
                lg3.operation = AttributeOperationEnum.ADD
                lg3.login = attrVal.value
                lg3.managedSysId = OPEN_LDAP
                pUser.principalList.add(lg3)

            }
        }
    }

    def addEmailAddress(ProvisionUser pUser, EmailAddress emailAddress) {
        if (!isNewUser) {
            for (EmailAddress e : pUser.emailAddresses) {
                if (emailAddress.metadataTypeId.equalsIgnoreCase(e.metadataTypeId)) {
                    e.updateEmailAddress(emailAddress)
                    e.setOperation(AttributeOperationEnum.REPLACE)
                    return
                }
            }
        }
        emailAddress.setOperation(AttributeOperationEnum.ADD)
        pUser.emailAddresses.add(emailAddress)
    }

    def addPhone(ProvisionUser pUser, Phone phone) {
        if (!isNewUser) {
            for (Phone p : pUser.phones) {
                if (phone.metadataTypeId.equalsIgnoreCase(p.metadataTypeId)) {
                    p.updatePhone(phone)
                    p.setOperation(AttributeOperationEnum.REPLACE)
                    return
                }
            }
        }
        phone.setOperation(AttributeOperationEnum.ADD)
        pUser.phones.add(phone)
    }

    def addAddress(ProvisionUser pUser, Address address) {
        if (!isNewUser) {
            for (Address a : pUser.addresses) {
                if (address.metadataTypeId.equalsIgnoreCase(a.metadataTypeId)) {
                    a.updateAddress(address)
                    a.setOperation(AttributeOperationEnum.REPLACE)
                    return
                }
            }
        }
        address.setOperation(AttributeOperationEnum.ADD)
        pUser.addresses.add(address)
    }

    def addOrganization(ProvisionUser pUser, String orgName, String orgType) {
        if (!isNewUser) {
            def foundOrg = pUser.affiliations.find { a-> a.organizationName == orgName && a.organizationTypeId == orgType }
            if (foundOrg) {
                return
            }
        }

        def organizationService = context?.getBean("organizationService") as OrganizationService
        def organizationDozerConverter = context?.getBean("organizationDozerConverter") as OrganizationDozerConverter

        def orgSearchBean = new OrganizationSearchBean()
        orgSearchBean.organizationName = orgName
        orgSearchBean.organizationTypeId = orgType
        def orgList = organizationService.findBeans(orgSearchBean, null, 0, 1)
        if (orgList) {
            def organization = organizationDozerConverter?.convertToDTO(orgList.get(0), false)
            organization.operation = AttributeOperationEnum.ADD
            pUser.affiliations.add(organization)
        }
    }

    def addAttribute(ProvisionUser pUser, Attribute attr) {
        if (attr?.name) {
            def userAttr = new UserAttribute(attr.name, attr.value)
            userAttr.operation = AttributeOperationEnum.ADD
            if (!isNewUser) {
                for (String name : pUser.userAttributes.keySet()) {
                    if (name.equalsIgnoreCase(attr.name)) {
                        pUser.userAttributes.remove(name)
                        userAttr.operation = AttributeOperationEnum.REPLACE
                        break
                    }
                }
            }
            pUser.userAttributes.put(attr.name, userAttr)
            println("Attribute '" + attr.name + "' added to the user object.")
        }
    }


    def addRole(ProvisionUser pUser, String roleName) {
        if (!isNewUser) {
            def foundRole = pUser.roles.find { r-> r.roleName == roleName }
            if (foundRole) {
                return
            }
        }
        def roleDataService = context?.getBean("roleDataService") as RoleDataService
        def roleDozerConverter = context?.getBean("roleDozerConverter") as RoleDozerConverter
        def role = roleDozerConverter?.convertToDTO(roleDataService?.getRoleByName(roleName, null), false)
        if (role) {
            role.operation = AttributeOperationEnum.ADD
            pUser.roles.add(role)
        } else {
            println "Role with name " + roleName + " was not found"
        }
    }

}
