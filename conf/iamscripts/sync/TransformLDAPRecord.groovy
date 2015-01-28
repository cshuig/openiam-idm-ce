import org.openiam.base.AttributeOperationEnum
import org.openiam.dozer.converter.RoleDozerConverter
import org.openiam.idm.srvc.continfo.dto.Address
import org.openiam.idm.srvc.continfo.dto.EmailAddress
import org.openiam.idm.srvc.role.service.RoleDataService
import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.synch.dto.LineObject
import org.openiam.idm.srvc.synch.service.AbstractTransformScript
import org.openiam.idm.srvc.synch.service.TransformScript
import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.provision.dto.ProvisionUser
import org.openiam.idm.srvc.user.dto.UserStatusEnum


public class TransformLDAPRecord extends AbstractTransformScript {
    String defaultRole = "End User"
    boolean assignDefaultRole = false

    @Override
    void init() {}
	
	public int execute(LineObject rowObj, ProvisionUser pUser) {

		println("** 2-Transform object called. **")
		println("Is New User: " + isNewUser)
        if (!isNewUser) {
            println("User Object:" + user)
            println("PrincipalList: " + principalList)
            println("User Roles:" + userRoleList)
        } else {
            pUser.id = null
        }
        populateObject(rowObj, pUser)
        pUser.status = UserStatusEnum.ACTIVE
        pUser.mdTypeId = "Contractor"
        // Add default role
        if (assignDefaultRole) {
            addRole(pUser, defaultRole)
        }
     //   return TransformScript.DELETE
        return TransformScript.NO_DELETE
	}

    private void populateObject(LineObject rowObj, ProvisionUser pUser) {

        def attrVal
		Map<String, Attribute> columnMap = rowObj.columnMap

		attrVal = columnMap.get("givenName")
		if (attrVal) {
			pUser.firstName = attrVal.value
		}

        attrVal = columnMap.get("sn")
		if (attrVal) {
			pUser.lastName = attrVal.value
		}

		attrVal = columnMap.get("employeeNumber")
		if (attrVal) {
			pUser.employeeId = attrVal.value
		}

		attrVal = columnMap.get("cn")
		if (attrVal) {
	        println("cn Value" + attrVal.value)
			addAttribute(pUser, attrVal)
		} else {
			println("cn attribute was not found")
		}
		
		attrVal = columnMap.get("uid")
		if (attrVal) {
			println("uid Value" + attrVal.value)
			addAttribute(pUser, attrVal)
		} else {
			println("cn attribute was not found")
		}

		attrVal = columnMap.get("mail")
		if (attrVal) {
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

        // Processing address
        def address = new Address()
        address.name = "PRIMARY_LOCATION"
        address.address1 = columnMap.get("street")?.value
        address.city = columnMap.get("l")?.value
        address.postalCd = columnMap.get("postalCode")?.value
        address.state = columnMap.get("st")?.value
        address.metadataTypeId = "PRIMARY_LOCATION"
        addAddress(pUser, address)
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
            def foundRole = pUser.roles.find { r-> r.name == roleName }
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
