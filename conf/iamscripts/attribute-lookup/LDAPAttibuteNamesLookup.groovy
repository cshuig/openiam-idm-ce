import org.openiam.idm.srvc.mngsys.service.AbstractAttributeNamesLookupScript

public class LDAPAttributeNamesLookup extends AbstractAttributeNamesLookupScript {

    @Override
    public Object lookupPolicyMapAttributes(Map<String, Object> binding) {

        def output = [
                'uid': 'NON_EDITABLE',
                'cn': 'NON_EDITABLE',
                'mail': 'EDITABLE',
                'o': 'EDITABLE',
                'ou': 'EDITABLE',
                'postalCode': 'EDITABLE',
                'sn': 'EDITABLE',
                'l': 'EDITABLE',
                'st': 'EDITABLE',
                'street': 'EDITABLE',
                'userPassword': 'HIDDEN',
                'postalAddress': 'EDITABLE',
                'telephoneNumber': 'EDITABLE',
                'facsimileTelephoneNumber': 'EDITABLE',
                'mobileTelephoneNumber': 'EDITABLE',
                'departmentNumber': 'EDITABLE',
                'displayName': 'EDITABLE',
                'employeeType': 'EDITABLE',
                'objectClass': 'HIDDEN',
                'title': 'EDITABLE',
                'givenName': 'EDITABLE',
                'description': 'EDITABLE',
                'uniqueMember': 'NON_EDITABLE',
                'manager': 'NON_EDITABLE',
                'createTimestamp': 'READ_ONLY',
                'modifyTimestamp': 'READ_ONLY'

        ].sort{a,b-> a.key <=> b.key} as Map

        return output
    }

    @Override
    public Object lookupManagedSystemAttributes(Map<String, Object> binding) {
        return ['PRE_PROCESS',
                'PRINCIPAL_PASSWORD',
                'INCLUDE_IN_PASSWORD_SYNC',
                'INCLUDE_USER_OBJECT',
                'ON_DELETE',
                'POST_PROCESS',
                'GROUP_MEMBERSHIP_ENABLED',
                'PRINCIPAL_NAME',
                'LOOKUP_USER_IN_OU',
                'ENABLE_ON_PASSWORD_RESET'
        ] as List
    }
}
