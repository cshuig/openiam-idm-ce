import org.openiam.idm.srvc.msg.service.MailService
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig
import org.openiam.idm.srvc.recon.service.ReconciliationServiceImpl
import org.openiam.idm.srvc.res.dto.Resource
import org.openiam.provision.service.AbstractPrePostExecutor
import org.apache.commons.lang.StringUtils

public class PrePostExecutorSample extends AbstractPrePostExecutor {
    @Override
    int execute(Map<String, Object> bindingMap) {
        ReconciliationConfig config = (ReconciliationConfig)bindingMap.get(ReconciliationServiceImpl.RECONCILIATION_CONFIG)
        this.sendMail(config);
        return 0;
    }

    private void sendMail(ReconciliationConfig config) {
        StringBuilder message = new StringBuilder();
        if (!StringUtils.isEmpty(config.getNotificationEmailAddress())) {
            message.append("Resource: " + config.managedSysId + ".\n");
            message.append("Uploaded CSV file: " + config.managedSysId + ".csv was successfully reconciled.\n");
            MailService mailService = context.getBean("mailService");
            mailService.sendEmails(null, new String[1] { config.getNotificationEmailAddress() }, null, null,
                    "CSVConnector", message.toString(), false, new String[0] {});
        }
    }
}