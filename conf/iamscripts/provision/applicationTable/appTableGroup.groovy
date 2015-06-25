import org.openiam.base.BaseAttribute
import org.openiam.base.BaseAttributeContainer
import org.openiam.base.BaseProperty
import org.openiam.idm.srvc.grp.service.GroupDataService

def groupManager = context.getBean("groupManager") as GroupDataService
def groupSet = groupManager.getGroupsForUser(user.id, "3000", -1, -1);

def attributeContainer = new BaseAttributeContainer();
groupSet?.each { g-> if (g.managedSystem?.id=='113') {
    List<BaseProperty> properties = new ArrayList<BaseProperty>();
    properties.add(new BaseProperty("GRP_ID", g.id, "principal=1;dataType=String"));
    properties.add(new BaseProperty("GRP_NAME", g.name, "principal=0;dataType=String"));
    BaseAttribute attr = new BaseAttribute("GROUP","GROUP");
    attr.properties = properties;
    attributeContainer.attributeList.add(attr);}
}
output = attributeContainer
