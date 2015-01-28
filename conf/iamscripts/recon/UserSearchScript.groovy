import groovy.json.JsonSlurper
import org.openiam.idm.searchbeans.UserSearchBean
import org.openiam.idm.srvc.recon.service.AbstractIDMSearchScript

public class UserSearchScript extends AbstractIDMSearchScript {
    @Override
    public UserSearchBean createUserSearchBean(Map<String, Object> bindingMap) {
        def bean = new UserSearchBean()
        if (updatedSince) {
            bean.updatedSince = updatedSince
        }
        if (searchFilter) {
            def obj = new JsonSlurper().parseText(searchFilter)
            if (obj in Map) {
                obj.keySet().each { key->
                    if (bean.properties.find{key}) {
                        bean."${key}" = obj."${key}"
                    }
                }
            }
        }
        return bean
    }
}