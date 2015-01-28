import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.synch.dto.LineObject
import org.openiam.idm.srvc.synch.service.ValidationScript
import org.springframework.context.ApplicationContext

class MAFValidationScript implements org.openiam.idm.srvc.synch.service.ValidationScript {

    ApplicationContext context

    public int isValid(LineObject rowObj) {
        println "** 1 - Validation script called."

        println "** 2 - Validation script completed and is valid."
       
        for(Map.Entry<String, Attribute> entry : rowObj.columnMap.entrySet()) {
            println(entry.key+" = "+entry.value);
        }
        if (org.apache.commons.lang.StringUtils.isNotEmpty(rowObj.columnMap.get("FIRST_NAME").value) && org.apache.commons.lang.StringUtils.isNotEmpty(rowObj.columnMap.get("LASTNAME").value)) {
            return org.openiam.idm.srvc.synch.service.ValidationScript.VALID
        }
        return org.openiam.idm.srvc.synch.service.ValidationScript.SKIP
    }

}
