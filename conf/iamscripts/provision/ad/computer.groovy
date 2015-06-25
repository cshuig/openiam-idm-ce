import org.openiam.base.AttributeOperationEnum
import org.openiam.base.BaseAttribute
import org.openiam.base.BaseAttributeContainer
import org.openiam.base.BaseProperty



    BaseAttributeContainer attributeContainer = new BaseAttributeContainer();
    List<BaseAttribute> attributeList = new ArrayList<BaseAttribute>();
    BaseAttribute attribute = new BaseAttribute("cn", "testCN");
    attributeList.add(attribute)
    attribute = new BaseAttribute("SamAccountName", "testSamAccountName");
    attributeList.add(attribute)
    attribute = new BaseAttribute("dnsName", "127.0.0.1");
    attributeList.add(attribute)
    attribute = new BaseAttribute("memberGroup", "dn");
    List<BaseProperty> baseProperties = new ArrayList<BaseProperty>();
    baseProperties.add(new BaseProperty("dn", "DN_test_Group_Member_admin1", "add"))
    baseProperties.add(new BaseProperty("dn", "DN_test_Group_Member_admin2", "add"))
    baseProperties.add(new BaseProperty("dn", "DN_test_Group_Member_admin3", "add"))
    baseProperties.add(new BaseProperty("dn", "DN_test_Group_Member_admin4", "add"))
    attribute.setProperties(baseProperties)
    attributeList.add(attribute)

    attributeContainer.setAttributeList(attributeList)
    output = attributeContainer