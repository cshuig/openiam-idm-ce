import org.openiam.base.AttributeOperationEnum
import org.openiam.idm.srvc.continfo.dto.Address
import org.openiam.idm.srvc.continfo.dto.EmailAddress
import org.openiam.idm.srvc.user.ws.UserDataWebService
import org.openiam.provision.dto.ProvisionUser
import org.openiam.idm.srvc.user.dto.UserStatusEnum

public class LDAPPopulationScript extends org.openiam.idm.srvc.recon.service.AbstractPopulationScript<ProvisionUser> {
    public int execute(Map<String, String> line, ProvisionUser pUser){

        int retval = 1

        def email = null
        def address = null
        if (pUser.id) {
            email = pUser.emailAddresses?.find{a-> a.metadataTypeId = 'PRIMARY_EMAIL' }
            address = pUser.addresses?.find{a-> a.metadataTypeId = 'PRIMARY_LOCATION' }
            if (!email || !address) {
                UserDataWebService userWS = context.getBean("userWS")
                if (!email) {
                    email = userWS.getEmailAddressList(pUser.id)?.find{a-> a.metadataTypeId = 'PRIMARY_EMAIL' }
                }
                if (!address) {
                    address = userWS.getAddressList(pUser.id)?.find{a-> a.metadataTypeId = 'PRIMARY_LOCATION' }
                }
            }
        }
        if (!email) {
            email = new EmailAddress(metadataTypeId: 'PRIMARY_EMAIL')
        }
        if (!address) {
            address = new Address(metadataTypeId: 'PRIMARY_LOCATION')
        }

        for(String key: line.keySet()) {
            switch(key) {
                case "uid":
                    //ignore for now - if this is changed no match can be established
                    break
                case "cn":
                    //
                    break
                case "mail":
                    if(email.emailAddress != line.get("mail")){
                        email.emailAddress = line.get("mail")
                        retval = 0
                    }
                    break
                case "o":
                    // fixed value for this LDAP managed sys
                    break
                case "ou":
                    // fixed value for this LDAP managed sys
                    break
                case "postalCode":
                    if(address.postalCd != line.get("postalCode")){
                        address.postalCd = line.get("postalCode")
                        retval = 0
                    }
                    break
                case "sn":
                    if(pUser.lastName != line.get("sn")){
                        pUser.lastName = line.get("sn")
                        retval = 0
                    }
                    break
                case "st":
                    if(address.state != line.get("st")){
                        address.state = line.get("st")
                        retval = 0
                    }
                    break
                case "l":
                    if(address.city != line.get("l")){
                        address.city = line.get("l")
                        retval = 0
                    }
                    break
                case "street":
                    //
                    break
                case "userPassword":
                    // not supported yet
                    break
                case "postalAddress":
                    if(address.address1 != line.get("postalAddress")){
                        address.address1 = line.get("postalAddress")
                        retval = 0
                    }
                    break
                case "displayName":
                    def parts = line.get("displayName").replaceAll(/\s+/,'').split(",")
                    if(parts.length == 2){
                        if(pUser.lastName != parts[0]){
                            pUser.lastName = parts[0]
                            retval = 0
                        }
                        if(pUser.firstName != parts[1]){
                            pUser.firstName = parts[1]
                            retval = 0
                        }
                    }
                    break
                case "employeeType":
                    if(pUser.employeeTypeId != line.get("employeeType")){
                        pUser.employeeTypeId = line.get("employeeType")
                        retval = 0
                    }
                    break
                case "objectClass":
                    // fixed in this ldap managed sys
                    break
                case "title":
                    if(pUser.title != line.get("title")){
                        pUser.title = line.get("title")
                        retval = 0
                    }
                    break
                case "givenName":
                    if(pUser.firstName != line.get("givenName")){
                        pUser.firstName = line.get("givenName")
                        retval = 0
                    }
                    break
            }
        }

        if (!pUser.id) {
            pUser.status = UserStatusEnum.PENDING_INITIAL_LOGIN
        }
        if (email.emailAddress) {
            email.operation = email.emailId? AttributeOperationEnum.REPLACE: AttributeOperationEnum.ADD
            pUser.emailAddresses.clear()
            pUser.emailAddresses.add(email)
        }
        if (address.address1 || address.postalCd || address.state || address.city) {
            address.operation = address.addressId? AttributeOperationEnum.REPLACE: AttributeOperationEnum.ADD
            pUser.addresses.clear()
            pUser.addresses.add(address)
        }

        return retval
    }
}