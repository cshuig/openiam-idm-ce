import org.openiam.base.AttributeOperationEnum
import org.openiam.dozer.converter.RoleDozerConverter
import org.openiam.idm.searchbeans.UserSearchBean
import org.openiam.idm.srvc.auth.dto.Login
import org.openiam.idm.srvc.auth.login.LoginDataService
import org.openiam.idm.srvc.continfo.dto.Address
import org.openiam.idm.srvc.continfo.dto.EmailAddress
import org.openiam.idm.srvc.org.dto.Organization
import org.openiam.idm.srvc.role.service.RoleDataService
import org.openiam.idm.srvc.user.dto.User
import org.openiam.idm.srvc.user.ws.UserDataWebService

import java.text.SimpleDateFormat

import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.synch.dto.LineObject
import org.openiam.idm.srvc.synch.service.AbstractTransformScript
import org.openiam.idm.srvc.synch.service.TransformScript
import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.provision.dto.ProvisionUser
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.idm.srvc.continfo.dto.Phone

public class MAFTransformCSVRecord extends AbstractTransformScript {

    @Override
    void init() {}

    @Override
    public int execute(LineObject rowObj, ProvisionUser pUser) {

        populateObject(rowObj, pUser)

        pUser.status = UserStatusEnum.ACTIVE

        pUser.mdTypeId = "Contractor"

        return TransformScript.NO_DELETE
    }

    private void populateObject(LineObject rowObj, ProvisionUser pUser) {

        def attrVal
        Map<String, Attribute> columnMap = rowObj.columnMap


        def df =  new SimpleDateFormat("MM/dd/yy")

        if (isNewUser) {
            pUser.id = null;
            for(Map.Entry<String, Attribute> entry : columnMap.entrySet()) {
                addAttribute(pUser, entry.value);
            }
        }

        attrVal = columnMap.get("ROLE")
        if (attrVal) {
            def names = attrVal.value.split(',')
            for(String n : names) {
                // Processing role
                addRole(pUser, n)
            }
        }
        attrVal = columnMap.get("EMPLOYEE_TYPE")
        if (attrVal) {
            pUser.employeeTypeId = attrVal.value
        }

        attrVal = columnMap.get("FIRST_NAME")
        if (attrVal) {
            pUser.firstName = attrVal.value
        }
        attrVal = columnMap.get("LASTNAME")
        if (attrVal) {
            pUser.lastName = attrVal.value
        }
        if (isNewUser) {
        /*  AD primary identity  */
            def lg = new Login()
            lg.operation = AttributeOperationEnum.ADD
            lg.login = pUser.firstName + "." +pUser.lastName

            def loginManager = context.getBean("loginManager") as LoginDataService

            def ctr = 1
            def origLoginID = lg.login

            while ( loginManager.loginExists( lg.login, "0")) {
                lg.login = origLoginID + ctr
                ctr++
            }


            lg.managedSysId = "0"
            pUser.principalList.add(lg)
        }
            // Processing email
            def email = new EmailAddress()
            email.name = "PRIMARY_EMAIL"
            email.emailAddress = columnMap.get("EMAIL")?.value
            email.metadataTypeId = "PRIMARY_EMAIL"
            addEmailAddress(pUser, email)

        //=================== MANAGER ===================================================

            //def superviser = columnMap.get("Manager Name")?.value
            def userManager = context.getBean("userWS") as UserDataWebService
            List<User> supervisorEntityList = pUser.id != null ? userManager.getSuperiors(pUser.id, 0, 100) : new LinkedList<User>();
            //println("Superviser=" + superviser);
            //if (superviser) {
            //    superviser = superviser.replaceAll(" ",".");
            def mangerEmailAddress = columnMap.get("Manager Email")?.value
            if (mangerEmailAddress) {
                def userDataService = context?.getBean("userWS") as UserDataWebService
                UserSearchBean searchBean = new UserSearchBean();
                searchBean.setEmailAddress(mangerEmailAddress);
                List<User> users = userDataService.findBeans(searchBean,0, 1);
                // User supervisorUser = userDataService.getUserByPrincipal(superviser, "0", false);
                //if (supervisorUser) {
                if (users != null && users.size() > 0) {
                    // println("Supervisor User found =" + superviser);
                    User supervisorUser = users.get(0);
                    println("Supervisor User found =" + users.get(0));
                    for (User s : supervisorEntityList) {
                        s.operation = AttributeOperationEnum.DELETE
                        pUser.addSuperior(s)

                    }
                    supervisorUser.operation = AttributeOperationEnum.ADD
                    pUser.addSuperior(supervisorUser)
                }
            }


    }

    def addEmailAddress(ProvisionUser pUser, EmailAddress emailAddress) {
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

    def addOrganization(ProvisionUser pUser, Organization organization) {
        organization.operation = AttributeOperationEnum.ADD
        if (!isNewUser) {
            for (Organization e : pUser.affiliations) {
                if (e.name == organization.name &&
                        e.organizationTypeId == organization.organizationTypeId) {
                    return //exists, skip it
                }
            }
        }
        pUser.affiliations.add(organization)
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

    def addAddress(ProvisionUser pUser, Address address) {
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

    def addPhone(ProvisionUser pUser, Phone phone) {
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
        }
    }

}
