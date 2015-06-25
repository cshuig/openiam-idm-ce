import org.openiam.base.AttributeOperationEnum
import org.openiam.idm.srvc.auth.dto.Login
import org.openiam.idm.srvc.auth.login.LoginDataService
import org.openiam.idm.srvc.continfo.dto.Address
import org.openiam.idm.srvc.mngsys.domain.ManagedSysEntity
import org.openiam.idm.srvc.mngsys.service.ManagedSystemService
import org.openiam.idm.srvc.res.service.ResourceService
import org.openiam.idm.srvc.user.service.UserDataService
import org.openiam.provision.dto.ProvisionUser
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.idm.srvc.user.dto.UserAttribute

import java.text.SimpleDateFormat

public class PowershellADPopulationScript extends org.openiam.idm.srvc.recon.service.AbstractPopulationScript<ProvisionUser> {

    def dateFormat = new SimpleDateFormat("yyyy-MM-dd")

    public int execute(Map<String, String> line, ProvisionUser pUser) {
        int retval = 1
        for(String key: line.keySet()) {
            switch(key) {
                case "SamAccountName":
                    addAttribute(pUser, "sAMAccountName", line.get("SamAccountName"))
                    break
                case "EmployeeID":
                    if(pUser.employeeId != line.get("EmployeeID")){
                        pUser.employeeId = line.get("EmployeeID")
                        retval = 0
                    }
                    addAttribute(pUser, "employeeID", line.get("EmployeeID"))
                    break
                case "Title":
                    if(pUser.title != line.get("Title")){
                        pUser.title = line.get("Title")
                        retval = 0
                    }
                    addAttribute(pUser, "title", line.get("Title"))
                    break
                case "GivenName":
                    if(pUser.firstName != line.get("GivenName")){
                        pUser.firstName = line.get("GivenName")
                        retval = 0
                    }
                    addAttribute(pUser, "givenName", line.get("GivenName"))
                    break
                case "Surname":
                    if(pUser.lastName != line.get("Surname")){
                        pUser.lastName = line.get("Surname")
                        retval = 0
                    }
                    addAttribute(pUser, "sn", line.get("Surname"))
                    break
                case "Initials":
                    if(pUser.middleInit != line.get("Initials")){
                        pUser.middleInit = line.get("Initials")
                        retval = 0
                    }
                    addAttribute(pUser, "initials", line.get("Initials"))
                    break
                case "OtherName":
                    if(pUser.nickname != line.get("OtherName")){
                        pUser.nickname = line.get("OtherName")
                        retval = 0
                    }
                    addAttribute(pUser, "otherName", line.get("OtherName"))
                    break
                case "AccountExpires":
                    if (line.get("AccountExpires")) {
                        def d = line.get("AccountExpires") as Long
                        GregorianCalendar win32Epoch = new GregorianCalendar(1601, Calendar.JANUARY, 1);
                        def ms = (d / 10000) + win32Epoch.getTimeInMillis() as Long
                        def accountExpiresDate = new Date(ms)
                        if (!accountExpiresDate.after(dateFormat.parse('9999-12-31'))) {
                            if (!pUser.lastDate?.equals(accountExpiresDate)) {
                                pUser.lastDate = accountExpiresDate
                                retval = 0
                            }
                        }
                    }
                    addAttribute(pUser, "accountExpires", line.get("AccountExpires"))
                    break
                case "City":
                    if (insertPrimaryAddressItem(pUser, 'city', line.get("City"))) {
                        retval = 0
                    }
                    addAttribute(pUser, "l", line.get("City"))
                    break
                case "State":
                    if (insertPrimaryAddressItem(pUser, 'state', line.get("State"))) {
                        retval = 0
                    }
                    addAttribute(pUser, "st", line.get("State"))
                    break
                case "HomeDirectory":
                    addAttribute(pUser, "homeDirectory", line.get("HomeDirectory"))
                    break
                case "HomeDrive":
                    addAttribute(pUser, "homeDrive", line.get("HomeDrive"))
                    break
                case "UserPrincipalName":
                    addAttribute(pUser, "userPrincipalName", line.get("UserPrincipalName"))
                    break
                case "DistinguishedName":
                    addAttribute(pUser, "distinguishedName", line.get("DistinguishedName"))
                    break
                case "ObjectGUID":
                    addAttribute(pUser, "objectGUID", line.get("ObjectGUID"))
                    break
                case "extensionAttribute1":
                    addAttribute(pUser, "extensionAttribute1", line.get("extensionAttribute1"))
                    break
                case "extensionAttribute2":
                    addAttribute(pUser, "extensionAttribute2", line.get("extensionAttribute2"))
                    break
                case "Managers":
                    if (line.get("Managers")) {
                        def principalName = line.get("Managers")
                        if (principalName) {
                            def loginManager = context.getBean("loginManager") as LoginDataService
                            def principals = loginManager.getLoginDetailsByManagedSys(principalName, managedSysId)
                            if (principals) {
                                def login = principals.get(0)
                                if (!pUser.superiors?.find {it.id == login.userId}) {
                                    pUser.superiors?.each { // remove previous managers if any
                                        it.operation = AttributeOperationEnum.DELETE
                                    }
                                    def userManager = context.getBean("userManager") as UserDataService
                                    def superior = userManager.getUserDto(login.userId)
                                    if (superior) {
                                        superior.operation = AttributeOperationEnum.ADD
                                        pUser.addSuperior(superior)
                                    }
                                }
                            }
                        }
                    }
                    addAttribute(pUser, "managers", line.get("Managers"))
                    break
            }
        }

        def managedSystemService = context.getBean(ManagedSystemService.class)
        def resourceService = context.getBean(ResourceService.class)
        def currentManagedSys = managedSystemService.getManagedSysById(managedSysId)
        def currentResource = resourceService.getResourceDTO(currentManagedSys.resourceId)
        if (!pUser?.resources?.find {it-> it.id == currentResource.id }) {
            currentResource.operation = AttributeOperationEnum.ADD
            pUser.resources.add(currentResource)
        }
        //set status to active: IMPORTANT!!!!
        if (!pUser.id) {
            pUser.status = UserStatusEnum.PENDING_INITIAL_LOGIN
        }
        if (!pUser.mdTypeId) {
            pUser.mdTypeId = "DEFAULT_USER"
        }
        //addExchangePrincipal(pUser, currentManagedSys, managedSystemService)
        return retval
    }

    def addExchangePrincipal(ProvisionUser pUser, ManagedSysEntity currentManagedSys, ManagedSystemService managedSystemService) {
        def exchangeManagedSys = managedSystemService.getManagedSysByName('MTS-WIN02-POWERSHELL-EXCHANGE2010')
        def found = pUser.principalList.find {Login l-> l.managedSysId == exchangeManagedSys.id }
        if (!found) {
            def currentLogin = pUser.principalList.find {Login l-> l.managedSysId == currentManagedSys.id }
            if (currentLogin) {
                def exchangeLogin = new Login()
                exchangeLogin.operation = AttributeOperationEnum.ADD
               // exchangeLogin.domainId = currentLogin.domainId
                exchangeLogin.login = currentLogin.login
                exchangeLogin.managedSysId = exchangeManagedSys.id
                pUser.principalList.add(exchangeLogin)
            }
        }
    }

    boolean insertPrimaryAddressItem(ProvisionUser pUser, String item, String value) {
        def address = pUser.addresses?.find { Address a-> a.metadataTypeId == 'PRIMARY_LOCATION' }
        def isNew = false
        if (!address) {
            isNew = true
            address = new Address()
            address.metadataTypeId = 'PRIMARY_LOCATION'
            pUser.addresses.add(address)
        }
        if (address."$item" != value) {
            if (address.operation == AttributeOperationEnum.NO_CHANGE) {
                address.operation = isNew ? AttributeOperationEnum.ADD : AttributeOperationEnum.REPLACE
            }
            address."$item" = value
            return true
        }
        return false
    }

    def addAttribute(ProvisionUser pUser, String attributeName, String attributeValue) {
        def userAttr = new UserAttribute(attributeName, attributeValue)
        if (!pUser.userAttributes.containsKey(attributeName)) {
            userAttr.operation = AttributeOperationEnum.ADD
        } else {
            if (userAttr.value != attributeValue) {
                userAttr.operation = AttributeOperationEnum.REPLACE
            }
        }
        pUser.userAttributes.put(attributeName, userAttr)
    }
}