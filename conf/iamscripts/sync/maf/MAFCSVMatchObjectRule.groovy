import org.apache.commons.lang.StringUtils
import org.openiam.idm.searchbeans.UserSearchBean
import org.openiam.idm.srvc.auth.ws.LoginDataWebService
import org.openiam.idm.srvc.auth.ws.LoginResponse
import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.idm.srvc.org.dto.Organization
import org.openiam.idm.srvc.recon.dto.MatchConfig
import org.openiam.idm.srvc.role.dto.Role
import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.synch.service.MatchObjectRule
import org.openiam.idm.srvc.user.domain.UserEntity
import org.openiam.idm.srvc.user.dto.User
import org.openiam.idm.srvc.user.service.UserDataService
import org.openiam.idm.srvc.user.ws.UserDataWebService
import org.openiam.provision.service.ProvisionService
import org.springframework.context.ApplicationContext

class MAFCSVMatchObjectRule implements MatchObjectRule {

    ApplicationContext context

    def matchAttrName = null
    def matchAttrValue = null

    @Override
    Group lookupGroup(MatchConfig matchConfig, Map<String, Attribute> rowAttr) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    Role lookupRole(MatchConfig matchConfig, Map<String, Attribute> rowAttr) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    Organization lookupOrganization(MatchConfig matchConfig, Map<String, Attribute> rowAttr) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    public User lookup(MatchConfig matchConfig, Map<String, Attribute> rowAttr) throws IllegalArgumentException {

        def searchBean = new UserSearchBean()

        String firstName = rowAttr.get("FIRST_NAME").value;
        String lastName = rowAttr.get("LASTNAME").value;

        String principal = firstName + "." + lastName;
        def loginWebDataManager = context?.getBean("loginWS") as LoginDataWebService
        def userWebDataManager = context?.getBean("userWS") as UserDataWebService

        LoginResponse loginResponse = loginWebDataManager.getLoginByManagedSys(principal, "0");


        if (loginResponse.principal != null) {
            println "User matched with existing user..."
            return userWebDataManager.getUserWithDependent(loginResponse.principal.userId,null,true);
        }


        return null
    }

    public String getMatchAttrName() {
        return matchAttrName
    }

    public void setMatchAttrName(String matchAttrName) {
        this.matchAttrName = matchAttrName
    }

    public String getMatchAttrValue() {
        return matchAttrValue
    }

    public void setMatchAttrValue(String matchAttrValue) {
        this.matchAttrValue = matchAttrValue
    }

    ApplicationContext getContext() {
        return context
    }

    void setContext(ApplicationContext context) {
        this.context = context
    }

    @Override
    public String toString() {
        return "CapsCSVMatchObjectRule {" +
                ", matchAttrName='" + matchAttrName + '\'' +
                ", matchAttrValue='" + matchAttrValue + '\'' +
                '}'
    }
}
