import org.openiam.idm.srvc.auth.dto.Login
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService
import org.openiam.idm.srvc.res.domain.ResourceEntity
import org.openiam.idm.srvc.res.domain.ResourcePropEntity
import org.openiam.idm.srvc.res.dto.Resource
import org.openiam.idm.srvc.res.dto.ResourceProp
import org.openiam.idm.srvc.res.service.ResourceDataService
import org.openiam.provision.dto.PasswordSync
import org.openiam.provision.dto.ProvisionUser
import org.openiam.provision.resp.LookupUserResponse
import org.openiam.provision.service.AbstractPreProcessor
import org.openiam.provision.service.ProvisionService
import org.openiam.provision.service.ProvisioningConstants
import org.openiam.provision.type.ExtensibleAttribute;
import org.springframework.context.ApplicationContext
import org.apache.commons.lang.StringUtils
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.security.spec.X509EncodedKeySpec;

public class ADPreProcessor extends AbstractPreProcessor<ProvisionUser> {
    String AD_MNG_SYS = "DD6CA4CC8BBC4D78A5879D93CEBC8A29";

	public int add(ProvisionUser user, Map<String, Object> bindingMap) {
		
		println("AD PreProcessor: AddUser called.");
		println("PreProcessor: User=" + user.toString());
		println("Show binding map");

        showBindingMap(bindingMap)
	
		return ProvisioningConstants.SUCCESS;
	}
	
    public int modify(ProvisionUser user, Map<String, Object> bindingMap){
    	
   		
    
    
    	return ProvisioningConstants.SUCCESS;
    
	}

    public int delete(ProvisionUser user, Map<String, Object> bindingMap) {
        ADLookup adLookup = new ADLookup(AD_MNG_SYS, context)
        String login = bindingMap.get("targetSystemIdentity").login;

        adLookup.fire(login, "DELETE");

        return ProvisioningConstants.SUCCESS;
    }


    public int setPassword(PasswordSync passwordSync, Map<String, Object> bindingMap) {
    
  		
    
    	return ProvisioningConstants.SUCCESS;
    
	}

    public int disable(ProvisionUser user, Map<String, Object> bindingMap) {
       if(bindingMap.get("operation")) {
           ADLookup adLookup = new ADLookup(AD_MNG_SYS, context)
           String login = bindingMap.get("targetSystemIdentity").login;

           adLookup.fire(login, "DISABLE");
       }
        return ProvisioningConstants.SUCCESS;
    }

    private void showBindingMap( Map<String, Object> bindingMap){
        // context to look up spring beans
        println("Show binding map:");
        for (Map.Entry<String, Object> entry : bindingMap.entrySet()) {
            def key = entry.key
            def val = entry.value as String
            if (key == 'password') {
                val = 'PROTECTED'
            }
            println("- Key=" + key + " value=" + val)
        }
    }

    class ADLookup {
        //Delimiter used in CSV file
        private final String COMMA_DELIMITER = ",";
        private final String NEW_LINE_SEPARATOR = "\n";

        //CSV file header
        private static final String FILE_HEADER = "User Login, Managed System ID, SamAccountName, UserCertificate, Date, Action";

        private final ProvisionService provisionService = null
        private final ResourceDataService dataService = null;
        private final ManagedSystemWebService managedSysService = null;
        private String mSysId
        private final List<ExtensibleAttribute> attributes
        private final String csvFilePath;

        public ADLookup(String managedSysId, ApplicationContext context) {
            provisionService = context.getBean('defaultProvision') as ProvisionService
            managedSysService =  context.getBean('managedSysService') as ManagedSystemWebService
            dataService = context.getBean('resourceDataService') as ResourceDataService
            ManagedSysDto mSys = managedSysService.getManagedSys(managedSysId);
            Resource res = dataService.getResource(mSys.getResourceId(), null);
            csvFilePath = getResourceProperty(res, "PKI_REVOCATION_FILE");
            mSysId = managedSysId
            attributes = new ArrayList<>()
            attributes.add(new ExtensibleAttribute("SamAccountName",""));
            attributes.add(new ExtensibleAttribute("UserCertificate",""));
        }

        private String getResourceProperty(final Resource resource, final String propertyName) {
            String retVal = null;
            if (resource != null && StringUtils.isNotBlank(propertyName)) {
                final ResourceProp property = resource.getResourceProperty(propertyName);
                if (property != null) {
                    retVal = property.getValue();
                }
            }
            return retVal;
        }

        public boolean fire(String login, String action) {
            LookupUserResponse resp = provisionService.getTargetSystemUser(login, mSysId, attributes)
            List<ExtensibleAttribute> attrs = resp.getAttrList();
            if(StringUtils.isNotBlank(csvFilePath)) {
                byte[] cert = null;
                String sAMAccountName = null;
                for(ExtensibleAttribute attr : attrs) {
                    if("SamAccountName".equalsIgnoreCase(attr.getName())) {
                        sAMAccountName = attr.getValue();
                    } else if("UserCertificate".equalsIgnoreCase(attr.getName())) {
                        cert = attr.getValueAsByteArray();
                    }
                }
                if(cert != null && sAMAccountName != null) {
                   writeCSVRecord(csvFilePath, login, AD_MNG_SYS, sAMAccountName,new String(cert), action);
                } else {
                    log.info("========= PKI revocation: " + (cert == null ? " cert is NULL, " : "") + (sAMAccountName == null ? " sAMAccountName" : ""))
                }
            } else {
                log.error("=========== PKI revocation: CSV File is not available ... Please check the attirbute = PKI_REVOCATION_FILE for Managed System ="+AD_MNG_SYS);
            }
            return resp.success
        }

        private boolean writeCSVRecord(String filePath, String userLogin, String managedSysId, String sAMAccountName, String permCert, String action) {

            DateTime dateTime = new DateTime(System.currentTimeMillis());
            DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
            String dateTimeString = fmt.print(dateTime);

            FileWriter fileWriter = null;

            try {
                boolean newFile = !new File(filePath).exists();
                fileWriter = new FileWriter(filePath, true);
                //Write the CSV file header
                if (newFile) {
                    fileWriter.append(FILE_HEADER.toString());
                    fileWriter.append(NEW_LINE_SEPARATOR);
                }


                fileWriter.append(userLogin);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(managedSysId);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(sAMAccountName);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(permCert);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(dateTimeString);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(action);
                fileWriter.append(NEW_LINE_SEPARATOR);

                log.info("CSV file was created successfully !!!");

            } catch (Exception e) {
                log.error("Error in CsvFileWriter !!!");
                e.printStackTrace();
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Error while flushing/closing fileWriter !!!");
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
    }
}
