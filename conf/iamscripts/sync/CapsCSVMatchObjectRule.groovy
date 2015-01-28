import org.apache.commons.lang.StringUtils
import org.openiam.idm.searchbeans.UserSearchBean
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
import org.springframework.context.ApplicationContext

class CapsCSVMatchObjectRule implements MatchObjectRule {

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
        matchAttrName = matchConfig .getMatchFieldName()
        matchAttrValue = StringUtils.isNotBlank(matchConfig.getCustomMatchAttr()) ? rowAttr.get(matchConfig.getCustomMatchAttr())?.value : null

        if (StringUtils.isBlank(matchAttrName) || StringUtils.isBlank(matchAttrValue)) {
            throw new IllegalArgumentException("matchAttrName and matchAttrValue can not be blank");
        }

        if (matchAttrName.equalsIgnoreCase("EMPLOYEE_ID")) {

            def customEmployeeId = matchAttrValue as Integer
            searchBean.setEmployeeId(customEmployeeId as String);

            def userWebDataManager = context?.getBean("userWS") as UserDataWebService
            List<User> userList = userWebDataManager.findBeans(searchBean, 0, Integer.MAX_VALUE);

            if (userList) {
                println "User matched with existing user..."
                return userList.get(0)
            }

        } else {
            throw new IllegalArgumentException("matchAttrName 'EMPLOYEE_ID' is supported only")
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
