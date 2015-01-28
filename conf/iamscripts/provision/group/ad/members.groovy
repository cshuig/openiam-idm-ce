import org.openiam.connector.common.command.ConnectorCommand
import org.openiam.connector.ldap.command.base.AbstractLdapCommand
import org.openiam.connector.ldap.command.user.ModifyUserLdapCommand
import org.openiam.connector.type.ConnectorDataException
import org.openiam.connector.type.constant.ErrorCode
import org.openiam.idm.srvc.auth.domain.LoginEntity
import org.openiam.idm.srvc.mngsys.domain.ManagedSysEntity
import org.openiam.idm.srvc.mngsys.domain.ManagedSystemObjectMatchEntity
import org.openiam.idm.srvc.mngsys.dto.ManagedSystemObjectMatch
import org.openiam.idm.srvc.mngsys.service.ManagedSystemService
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService
import org.openiam.idm.srvc.user.domain.UserEntity
import org.openiam.idm.srvc.user.dto.User
import org.openiam.idm.srvc.user.service.UserDataService
import org.openiam.provision.dto.ProvisionUser

import org.apache.commons.lang.StringUtils
import org.openiam.provision.resp.LookupUserResponse
import org.openiam.provision.service.DefaultProvisioningService
import org.openiam.provision.type.ExtensibleAttribute

import javax.naming.NameNotFoundException
import javax.naming.NamingEnumeration
import javax.naming.directory.ModificationItem
import javax.naming.directory.SearchResult
import javax.naming.ldap.LdapContext
import java.text.MessageFormat
import java.util.regex.Matcher
import java.util.regex.Pattern



output = null;
//Provisioning Members
if (binding.hasVariable("managedSysId")) {
    org.openiam.provision.service.ProvisionService provisionService = context.getBean("defaultProvision");
    ManagedSystemWebService managedSystemWebService = context.getBean(ManagedSystemWebService.class)
    UserDataService userDataService = context.getBean(UserDataService.class);
    List<UserEntity> members = userDataService.getUsersForGroup(group.getId(), requesterId, 0, Integer.MAX_VALUE);
    List<String> memberDNList = new ArrayList<String>();

    ManagedSystemObjectMatch matchObj = null;
    ManagedSystemObjectMatch[] objList = managedSystemWebService.managedSysObjectParam(managedSysId,
            ManagedSystemObjectMatch.USER);
    if (objList.length > 0) {
        matchObj = objList[0];
    }
    for(UserEntity member : members) {

       String principal = null;
       for(LoginEntity login : member.principalList) {
          if (login.managedSysId.equalsIgnoreCase(managedSysId)) {
              principal = login.login;
              break;
          }
       }
        String DN = "";
        List<ExtensibleAttribute> attributes = new LinkedList<>();
        attributes.add(new ExtensibleAttribute("distinguishedName","","string"));
        LookupUserResponse lookupUserResponse = provisionService.getTargetSystemUser(principal, managedSysId,attributes);
        if (lookupUserResponse.isSuccess() && lookupUserResponse.attrList != null && lookupUserResponse.attrList.size() >0) {
            for(ExtensibleAttribute attribute : lookupUserResponse.getAttrList()){
               if ("distinguishedName".equalsIgnoreCase(attribute.getName())){
                   DN  = attribute.getValue();
                   break;
               }

            }
        }
        if (StringUtils.isNotEmpty(DN)) {
            memberDNList.add(DN);
        }

    }
    if (memberDNList.size() > 0) {
       output = memberDNList;
    }
}
