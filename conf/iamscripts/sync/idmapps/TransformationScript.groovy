package sync.idmapps

import org.openiam.base.AttributeOperationEnum
import org.openiam.base.ws.Response
import org.openiam.dozer.converter.OrganizationDozerConverter
import org.openiam.dozer.converter.RoleDozerConverter
import org.openiam.idm.searchbeans.OrganizationSearchBean
import org.openiam.idm.srvc.auth.dto.Login
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
import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.provision.dto.ProvisionUser
import org.springframework.context.ApplicationContext

class TransformationScript extends AbstractTransformScript {

    private ApplicationContext context

    OrganizationDataService orgService

    @Override
    int execute(LineObject rowObj, ProvisionUser pUser) {
        orgService = context?.getBean("orgManager") as OrganizationDataService;
        println "** - Transformation script called."
        try {
            populateObject(rowObj, pUser)
        }catch(Exception ex) {
            ex.printStackTrace();
            println "** - Transformation script error."
            return -1;
        }
        pUser.status = UserStatusEnum.ACTIVE
        println "** - Transformation script completed."

        pUser.setSkipPreprocessor(true)
        pUser.setSkipPostProcessor(true)

        return TransformScript.NO_DELETE
    }

    private void populateObject(LineObject rowObj, ProvisionUser pUser) {

        def columnMap =  rowObj.columnMap
        if (isNewUser) {
            pUser.id = null
        }
        pUser.employeeId = columnMap.get("USER_ID")?.value
        if (pUser.employeeId.length() > 15) {
            pUser.employeeId = pUser.employeeId.substring(0,15)
        }
        pUser.firstName = columnMap.get("FIRST_NAME")?.value
        pUser.lastName = columnMap.get("LAST_NAME")?.value
        pUser.middleInit = columnMap.get("MIDDLE_INIT")?.value
        pUser.mdTypeId = columnMap.get("TYPE_ID")?.value


        Attribute attrVal = columnMap.get("GRAD_YEAR");
        if (isNewUser) {
            Thread.sleep(500)
            if (attrVal != null && attrVal.value != null) {
                addAttribute(pUser, attrVal);
            }
            attrVal = columnMap.get("GRADE");
            String grade
            if (attrVal != null && attrVal.value != null) {
                addAttribute(pUser, attrVal);
                grade = attrVal.value;
            }

            attrVal = columnMap.get("UIC");
            if (attrVal != null && attrVal.value != null) {
                addAttribute(pUser, attrVal);
            }

            attrVal = columnMap.get("BUILDING");
            String schoolName
            if (attrVal != null && attrVal.value != null) {
                addAttribute(pUser, attrVal);
                schoolName = attrVal.value;
            }
            attrVal = columnMap.get("DISTRICT");
            String district
            if (attrVal != null && attrVal.value != null) {

                if (attrVal.value.length() < 5) {
                    attrVal.value = "0" + attrVal.value;
                }
                addAttribute(pUser, attrVal);
                district =  attrVal.value
            }

            attrVal = columnMap.get("STUDENTNUMBER");
            if (attrVal != null && attrVal.value != null) {
                addAttribute(pUser, attrVal);
            }


            pUser.mdTypeId = "DEFAULT_USER"

            //       println "** 3 - Transformation script completed."
            // Processing organization
            // Add Organization before Email calculation
            if (schoolName != null) {
                addOrganization(pUser, schoolName, district)
            }
            //       println "** 4 - Transformation script completed."
            // Processing email
            def email = new EmailAddress()
            try{
                email.name = "PRIMARY_EMAIL"
                email.metadataTypeId = "PRIMARY_EMAIL"

                email.emailAddress = columnMap.get("EMAIL")?.value

                if (org.apache.commons.lang.StringUtils.isEmpty(email.emailAddress)) {
                    if (pUser.middleInit == null) {
                        email.emailAddress = pUser.firstName + "." + pUser.lastName;
                    }else {
                        email.emailAddress = pUser.firstName + "." + pUser.middleInit.substring(0,1)  + "." + pUser.lastName
                    }
                }
                email.emailAddress = email.emailAddress.replaceAll("\\s+","")   + (schoolName != null ? getMailDomain( schoolName) : "");
                addEmailAddress(pUser,email);

//            println "** 4.1 - Transformation script completed."
            }catch(Exception ex) {
                ex.printStackTrace();
            }
//        println "** 5 - Transformation script completed."
        }

        try {

            attrVal = columnMap.get("USERNAME");
            if (isNewUser) {
                println("Checking  userName...");
//                println "** 5.1 - Transformation script completed."
                if (attrVal != null && attrVal.value != null) {
//                    println "** 5.1.1 - Transformation script completed."
                    attrVal.value = "X" + attrVal.value;
                    println("user name found: " + attrVal.value)

                    // we have an ID
                    Login lg = new Login();
                    lg.setLogin(attrVal.value)
                    pUser.login=attrVal.value;
                    lg.setManagedSysId("0")
                    lg.setOperation(AttributeOperationEnum.ADD)
                    Attribute attrVal2 = columnMap.get("PASSWORD");
                    if (attrVal2 != null && attrVal2.value != null) {
                        println("Assiging password : " + attrVal2.value);
                        lg.password = attrVal2.value;
                        pUser.password=attrVal2.value;
                    } else {
                        println("Password not found..");
                    }
//                    println "** 5.1.2 - Transformation script completed."
                    pUser.principalList.add(lg)
                }
//                println "** 5.2 - Transformation script completed."
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //        println "** 6 - Transformation script completed."
        // Processing role
 //       if (isNewUser) {
           addRole(pUser, "STUDENT")
 //       }
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

    private addOrganization(ProvisionUser pUser, String orgName, String parentInternalOrgId) {
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
                newOrg.setDomainName("oaisd.org")
                Response resp = orgService.saveOrganization(newOrg, null);
                String newOrgId = resp.getResponseValue();

                // Add parent by parent org internal Id
                OrganizationSearchBean searchParentBeanP = new OrganizationSearchBean();
                searchParentBeanP.setDeepCopy(false);
                searchParentBeanP.setInternalOrgId(parentInternalOrgId);
                List<Organization> orgParents = orgService.findBeans(searchParentBeanP, null, -1, -1);
                Organization foundParentOrg;
                if (orgParents != null && orgParents.size() > 0 && newOrgId != null) {
                    foundParentOrg = orgParents.get(0)
                    orgService.addChildOrganization(foundParentOrg.id, newOrgId)
                    println("Parent for Organization by DISTRICT="+parentInternalOrgId+" was found = "+foundParentOrg.getName()+"")
                } else {
                    println("Parent for Organization by DISTRICT="+parentInternalOrgId+" wasn't found!")
                }

            }
            def foundOrg = pUser.affiliations.find { o-> o.name == orgName && o.organizationTypeId == 'ORGANIZATION' }
            if (!foundOrg) {
                def organizationService = context?.getBean("organizationService") as OrganizationService
                def organizationDozerConverter = context?.getBean("organizationDozerConverter") as OrganizationDozerConverter
                def org = organizationDozerConverter?.convertToDTO(organizationService?.getOrganizationByName(orgName, null, null), false)

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