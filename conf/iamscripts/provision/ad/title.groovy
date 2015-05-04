import org.apache.commons.lang.StringUtils
import org.openiam.base.AttributeOperationEnum
import org.openiam.base.BaseConstants
import org.openiam.provision.type.ExtensibleAttribute

output = null
def title = (user.title && (user.title != BaseConstants.NULL_STRING))? user.title : null
if (StringUtils.isNotBlank(title)) {
    output = title
} else {
    output = new ExtensibleAttribute(name: attributeMap.attributeName, operation: AttributeOperationEnum.DELETE.value, value: 'none')
}