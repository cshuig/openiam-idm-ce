import org.openiam.base.AttributeOperationEnum
import org.openiam.dozer.converter.OrganizationDozerConverter
import org.openiam.dozer.converter.RoleDozerConverter
import org.openiam.idm.srvc.continfo.dto.Address
import org.openiam.idm.srvc.continfo.dto.EmailAddress
import org.openiam.idm.srvc.continfo.dto.Phone
import org.openiam.idm.srvc.org.service.OrganizationService
import org.openiam.idm.srvc.role.service.RoleDataService
import org.openiam.idm.srvc.synch.dto.LineObject
import org.openiam.idm.srvc.synch.service.AbstractTransformScript
import org.openiam.idm.srvc.synch.service.TransformScript
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.provision.dto.ProvisionUser
import org.springframework.context.ApplicationContext

class TestTransformationScript extends AbstractTransformScript {

    private ApplicationContext context

    @Override
    int execute(LineObject rowObj, ProvisionUser pUser) {
        println "** 1 - Transformation script called."

        populateObject(rowObj, pUser)

        pUser.status = UserStatusEnum.ACTIVE

        println "** 2 - Transformation script completed."

        TransformScript.NO_DELETE
    }

    private void populateObject(LineObject rowObj, ProvisionUser pUser) {

        def columnMap =  rowObj.columnMap

        pUser.employeeId = columnMap.get("EMPLOYEE_ID")?.value
        pUser.firstName = columnMap.get("FIRST_NAME")?.value
        pUser.lastName = columnMap.get("LAST_NAME")?.value
        pUser.middleInit = columnMap.get("MIDDLE_INIT")?.value

        // Processing address
        def address = new Address()
        address.name = "PRIMARY_LOCATION"
        address.address1 = columnMap.get("ADDRESS")?.value
        address.city = columnMap.get("CITY")?.value
        address.postalCd = columnMap.get("ZIP")?.value
        address.state = columnMap.get("STATE")?.value
        address.metadataTypeId = "PRIMARY_LOCATION"
        addAddress(pUser, address)

        // Processing phone
        def phone = new Phone()
        phone.name = "OFFICE_PHONE"
        phone.countryCd = ''
        phone.areaCd = ''
        phone.phoneNbr = columnMap.get("PHONE")?.value
        phone.metadataTypeId = "OFFICE_PHONE"
        addPhone(pUser, phone)

        // Processing email
        def email = new EmailAddress()
        email.name = "PRIMARY_EMAIL"
        email.emailAddress = columnMap.get("EMAIL")?.value
        email.metadataTypeId = "PRIMARY_EMAIL"
        addEmailAddress(pUser, email)

        // Processing organization
        addOrganization(pUser, columnMap.get("ORGANIZATION")?.value)

        // Processing role
        addRole(pUser, columnMap.get("ROLE")?.value)

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
        def foundOrg = pUser.affiliations.find { o-> o.organizationName == orgName && o.organizationTypeId == 'ORGANIZATION' }
        if (!foundOrg) {
            def organizationService = context?.getBean("organizationService") as OrganizationService
            def organizationDozerConverter = context?.getBean("organizationDozerConverter") as OrganizationDozerConverter
            def org = organizationDozerConverter?.convertToDTO(organizationService?.getOrganizationByName(orgName, null, null), false)
            if (org) {
                org.operation = AttributeOperationEnum.ADD
                pUser.affiliations.add(org)
            }
        }
    }

    def addRole(ProvisionUser pUser, String roleName) {
        def foundRole = pUser.roles.find { r-> r.roleName == roleName }
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

    @Override
    void init() {}

}
