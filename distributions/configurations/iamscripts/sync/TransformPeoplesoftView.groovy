import org.openiam.base.AttributeOperationEnum
import org.openiam.idm.srvc.role.dto.Role
import org.openiam.idm.srvc.role.dto.RoleId
import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.synch.dto.LineObject
import org.openiam.idm.srvc.synch.service.AbstractTransformScript
import org.openiam.idm.srvc.synch.service.TransformScript
import org.openiam.idm.srvc.user.dto.UserAttribute
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.provision.dto.ProvisionUser

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import org.openiam.idm.srvc.role.ws.RoleDataWebService
import javax.xml.ws.Service
import javax.xml.namespace.QName
import javax.xml.ws.soap.SOAPBinding
import org.openiam.idm.srvc.role.dto.RoleSearch
import org.openiam.base.ObjectSearchAttribute

public class TransformPeoplesoftView extends AbstractTransformScript {

    static String BASE_URL = "http://localhost:8080/openiam-idm-esb/idmsrvc/";

    static String DOMAIN = "USR_SEC_DOMAIN";
    static boolean KEEP_AD_ID = true;
    static String MANAGED_SYS_ID = "8ab98e973c86d853013c87633fb40010";
    static String DEFAULT_MANAGED_SYS = "0";
    static String ORG_ID = "8ab98e973d718c42013d719007e10001";

    RoleDataWebService roleDataWebService = roleService();



    public int execute(LineObject rowObj, ProvisionUser pUser) {
       //We wait until AD processing request
       try {
           java.lang.Thread.sleep(500);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
        println("TransformPeoplesoftView called");
	println("RoleDataService=" + roleDataWebService );

        // make the assignment so that its more readable
        List<Role> existingRoles = userRoleList;

        if (isNewUser) {
            pUser.setUserId(null);
        } else {
            pUser.setUserAttributes(user.getUserAttributes());
        }

        populateObject(rowObj, pUser);

        // this configure the loading Pre/Post groovy scrips, should be switch off for performance
        pUser.setSkipPostProcessor(true);
        pUser.setSkipPreprocessor(true);

        pUser.setStatus(UserStatusEnum.PENDING_INITIAL_LOGIN);
        pUser.securityDomain = "0";
        pUser.companyId = ORG_ID;

        boolean foundJobRole = false;

println("Processing roles for jobcode:" + pUser.jobCode);
        if (pUser.jobCode != null) {

            RoleSearch search = new RoleSearch();
            ObjectSearchAttribute searchAttribute = new ObjectSearchAttribute();
            searchAttribute.attributeName = "JOB_CODE";
            searchAttribute.attributeValue = pUser.jobCode;
            List<ObjectSearchAttribute> searchAttributeList = new ArrayList<ObjectSearchAttribute>();
            searchAttributeList.add(searchAttribute);
            search.searchAttributeList = searchAttributeList;


println("Searching for roles linked to this jobcode =" + search);
try{
    List<Role> matchingRole =  roleDataWebService.search(new RoleSearch()).roleList;

println("matching role=" + matchingRole);

            if (matchingRole != null && !matchingRole.isEmpty()) {
                foundJobRole = true;

                List<Role> roleList = new ArrayList<Role>();
                RoleId id = matchingRole.get(0).id;
                Role r = new Role();
                r.setId(id)
                roleList.add(r);
                pUser.setMemberOfRoles(roleList);

            }
}catch(Exception e) {
	e.printStacktrace();
}


        }

        if (!foundJobRole) {
            println("No role for jobcode found:" );
            // putthis user in the UnProvisionedRole
            // remove any other roles that this user may have
            List<Role> roleList = new ArrayList<Role>();
            RoleId id = new RoleId(DOMAIN, "UN-PROVISIONED");
            Role r = new Role();
            r.setId(id)
            roleList.add(r);
            // REMOVE ANY EXISTING ROLES
            if ( existingRoles != null && !existingRoles.isEmpty()) {
                for ( Role rl : existingRoles ) {
                    rl.operation = AttributeOperationEnum.DELETE;
                    roleList.add(rl);
                }
            }

            pUser.setMemberOfRoles(roleList);

        }
        return TransformScript.NO_DELETE;
    }


    private void populateObject(LineObject rowObj, ProvisionUser pUser) {
        Attribute attrVal = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yy");


        Map<String, Attribute> columnMap = rowObj.getColumnMap();


        attrVal = columnMap.get("LAST_NAME");
        if (attrVal != null && attrVal.value != null) {
            pUser.lastName = attrVal.getValue();
        }
        attrVal = columnMap.get("FIRST_NAME");
        if (attrVal != null && attrVal.value != null) {
            pUser.firstName = attrVal.getValue();
        }
        attrVal = columnMap.get("MIDDLE_NAME");
        if (attrVal != null && attrVal.value != null) {
            pUser.middleInit = attrVal.getValue();
        }
        attrVal = columnMap.get("NATIONAL_ID");
        if (attrVal != null && attrVal.value != null) {
            addAttribute(pUser, attrVal);
        }
        attrVal = columnMap.get("JOBCODE");
        if (attrVal != null && attrVal.value != null) {
            pUser.jobCode = attrVal.getValue();
        }
        attrVal = columnMap.get("DESCR_AC");
        if (attrVal != null && attrVal.value != null) {
            pUser.title = attrVal.getValue();
        }
        attrVal = columnMap.get("LOCATION");
        if (attrVal != null && attrVal.value != null) {
            // integrate with attribute in AD
            attrVal.name = "physicalDeliveryOfficeName";
            addAttribute(pUser, attrVal);
        }

        // process the dates that coming from peoplesoft

        try {

            attrVal = columnMap.get("BIRTHDATE");
            if (attrVal != null && attrVal.value != null) {

                println("parsing birthdate: " + attrVal.value);

                pUser.birthdate = df.parse(attrVal.value);

            }

            attrVal = columnMap.get("TERMINATION_DT");
            if (attrVal != null && attrVal.value != null) {

                println("parsing last hiredate: " + attrVal.value);

                pUser.startDate = df.parse(attrVal.value);

            }


        } catch (ParseException parseException) {
            println(" Failed to parse date: " + parseException.getMessage());
        }

        // if this is a new user then build the identities for this user
        if (isNewUser) {

            attrVal = columnMap.get("EMPLID");
            if (attrVal != null && attrVal.value != null) {
                pUser.employeeId = attrVal.getValue();
            }
        }


    }

    private void addAttribute(ProvisionUser u, Attribute attr) {

        if (attr != null && attr.getName() != null && attr.getName().length() > 0) {

            UserAttribute existingAttr = u.getUserAttributes().get(attr.getName());
            if (existingAttr != null) {
                existingAttr.setValue(attr.getValue());
                existingAttr.setOperation(AttributeOperationEnum.REPLACE);

            } else {
                UserAttribute userAttr = new UserAttribute(attr.getName(), attr.getValue());
                userAttr.setOperation(AttributeOperationEnum.ADD);
                if (u.getUserAttributes() != null) {
                    u.getUserAttributes().put(userAttr.getName(), userAttr);

                }
                else {

                    Map attrMap = new HashMap<UserAttribute>();
                    attrMap.put(userAttr);
                    u.setUserAttributes(attrMap);

                }
            }
        }
    }

    private RoleDataWebService roleService() {
        ResourceBundle res = ResourceBundle.getBundle("datasource");
        String BASE_URL =  res.getString("openiam.service_host") + res.getString("openiam.idm.ws.path");


        String serviceUrl = BASE_URL + "RoleDataWebService"
        String port ="RoleDataWebServicePort"
        String nameSpace = "urn:idm.openiam.org/srvc/role/service"

        Service service = Service.create(QName.valueOf(serviceUrl))

        service.addPort(new QName(nameSpace,port),
                SOAPBinding.SOAP11HTTP_BINDING,	serviceUrl)

        return service.getPort(new QName(nameSpace,	port),
                RoleDataWebService.class);
    }


}
