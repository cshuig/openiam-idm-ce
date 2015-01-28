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

    UserDataService userDataService = context.getBean(UserDataService.class);
    List<UserEntity> members = userDataService.getUsersForGroup(group.getId(), requesterId, 0, Integer.MAX_VALUE);
    List<String> memberDNList = new ArrayList<String>();


    ManagedSystemWebService managedSystemWebService = context.getBean(ManagedSystemWebService.class)
    ManagedSystemService managedSystemService = context.getBean(ManagedSystemService.class)

    def ModifyUserLdapCommand command = context.getBean("modifyUserLdapCommand");
    ManagedSysEntity managedSysEntity =  managedSystemService.getManagedSysById(managedSysId);

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
       if (principal != null) {
           //generation DN for member
           //Check identity on DN format or not
           String identityPatternStr =  MessageFormat.format(AbstractLdapCommand.DN_IDENTITY_MATCH_REGEXP, matchObj.getKeyField());
           Pattern pattern = Pattern.compile(identityPatternStr);
           Matcher matcher = pattern.matcher(principal);

           String objectBaseDN;
           if(matcher.matches()) {
               String identity = matcher.group(1);
               String CN = matchObj.getKeyField()+"="+identity;
               objectBaseDN =  principal.substring(CN.length()+1);
               principal = identity;
           } else {
               // if identity is not in DN format try to find OU info in attributes
               String OU = member.userAttributes?.get("OU");
               if(StringUtils.isNotEmpty(OU)) {
                   objectBaseDN = OU+","+matchObj.getBaseDn();
               } else {
                   objectBaseDN = matchObj.getBaseDn();
               }
           }

           //Important!!! For save and modify we need to create DN format
       //     String identityDN = matchObj.getKeyField() + "=" + principal + "," + objectBaseDN;

           NamingEnumeration results = null;
           try {
               println("Looking for user with identity=" +  principal + " in " +  objectBaseDN);

               LdapContext ldapctx = command.connect(managedSysEntity);
               results = command.lookupSearch(managedSysEntity, matchObj, ldapctx, principal, null, objectBaseDN);

           } catch (NameNotFoundException e) {
               println("results=NULL");
               println(" results has more elements=0");
               break;
           }

           String identityDN = null;
           int count = 0;
           while (results != null && results.hasMoreElements()) {
               SearchResult sr = (SearchResult) results.next();
               identityDN = sr.getNameInNamespace();
               count++;
           }

           if (count == 0) {
               String err = String.format("User %s was not found in %s", principal, objectBaseDN);
               println(err);
           } else if (count > 1) {
               String err = String.format("More then one user %s was found in %s", principal, objectBaseDN);
               println(err);
           }

           if (StringUtils.isNotEmpty(identityDN)) {
               memberDNList.add(identityDN);
           }

       }
    }
    if (memberDNList.size() > 0) {
       output = memberDNList;
    }
}
