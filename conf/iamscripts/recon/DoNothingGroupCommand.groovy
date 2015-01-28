import org.openiam.base.BaseAttribute
import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation
import org.openiam.idm.srvc.recon.service.PopulationScript
import org.openiam.idm.srvc.recon.service.ReconciliationObjectAbstractCommand
import org.openiam.provision.dto.ProvisionGroup
import org.openiam.provision.service.AbstractProvisioningService
import org.openiam.provision.type.ExtensibleAttribute
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils
import org.openiam.script.ScriptIntegration;

public class DoNothingGroupCommand extends ReconciliationObjectAbstractCommand<Group> {
    private static final Log log = LogFactory.getLog(DoNothingGroupCommand.class);

    @Override
    boolean execute(ReconciliationSituation config, String principal, String mSysID, Group group, List<ExtensibleAttribute> attributes) {
        log.debug("Entering DoNothingCommand Groovy");
        log.debug("Do nothing for Group :" + principal);
        ProvisionGroup pGroup = new ProvisionGroup(group);
        pGroup.setSrcSystemId(mSysID);
        if(StringUtils.isNotEmpty(config.getScript())){
            try {
                Map<String, String> line = new HashMap<String, String>();
                for (ExtensibleAttribute attr : attributes) {
                    if (attr.getValue() != null) {
                        line.put(attr.getName(), attr.getValue());
                    } else if (attr.getAttributeContainer() != null &&
                            CollectionUtils.isNotEmpty(attr.getAttributeContainer().getAttributeList()) &&
                            line.get(attr.getName()) == null) {
                        StringBuilder value = new StringBuilder();
                        boolean isFirst = true;
                        for (BaseAttribute ba : attr.getAttributeContainer().getAttributeList()) {
                            if (!isFirst) {
                                value.append('^');
                            } else {
                                isFirst = false;
                            }
                            value.append(ba.getValue());
                        }
                        line.put(attr.getName(), value.toString());
                    }
                }
                Map<String, Object> bindingMap = new HashMap<String, Object>();
                bindingMap.put(AbstractProvisioningService.TARGET_SYS_MANAGED_SYS_ID, mSysID);
                ScriptIntegration scriptRunner = context.getBean("configurableGroovyScriptEngine");
                PopulationScript<ProvisionGroup> script = (PopulationScript<ProvisionGroup>) scriptRunner.instantiateClass(bindingMap, config.getScript());
                int retval = script.execute(line, pGroup);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

}