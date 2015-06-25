import org.openiam.idm.srvc.mngsys.service.AbstractAttributeNamesLookupScript

public class LDAPAttributeNamesLookup extends AbstractAttributeNamesLookupScript {

    @Override
    public Object lookupPolicyMapAttributes(Map<String, Object> binding) {

        def output = [];

        return output
    }

    @Override
    public Object lookupManagedSystemAttributes(Map<String, Object> binding) {
        return ['USER_TABLE',
                'GROUP_TABLE',
                'USER_GROUP_MEMBERSHIP',
                'GROUP_GROUP_MEMBERSHIP',
                'GROUP_GROUP_MEMBERSHIP_GRP_ID',
                'GROUP_GROUP_MEMBERSHIP_GRP_CHLD_ID',
		'GROUP_TO_GROUP_PK_COLUMN_NAME',
                'USER_GROUP_MEMBERSHIP_GRP_ID',
                'USER_GROUP_MEMBERSHIP_USR_ID',
		'USER_TO_GROUP_PK_COLUMN_NAME',
                'PRINCIPAL_PASSWORD',
                'USER_STATUS_FIELD',
                'USER_STATUS_ACTIVE',
                'USER_STATUS_INACTIVE',
 		'GROUP_MEMBERSHIP_ENABLED',
		'ON_DELETE',
		'INCLUDE_IN_PASSWORD_SYNC',
		'INCLUDE_IN_STATUS_SYNC',
                'ENABLE_ON_PASSWORD_RESET'
        ] as List
    }
}
