import org.openiam.idm.srvc.mngsys.service.AbstractAttributeNamesLookupScript

public class ADAttributeNamesLookup extends AbstractAttributeNamesLookupScript {

    @Override
    public Object lookupPolicyMapAttributes(Map<String, Object> binding) {

        def output = [
                sAMAccountName: 'NON_EDITABLE',
                ou: 'EDITABLE',
                sn: 'EDITABLE',
                l: 'EDITABLE',
                st: 'EDITABLE',
                givenName: 'EDITABLE',
                displayName: 'EDITABLE',
                objectClass: 'HIDDEN',
                profilePath: 'EDITABLE',
                homeDirectory: 'EDITABLE',
                homeDrive: 'EDITABLE',
                scriptPath: 'EDITABLE',
                company: 'EDITABLE',
                initials: 'EDITABLE',
                department: 'EDITABLE',
                telephoneNumber: 'EDITABLE',
                title: 'EDITABLE',
                unicodePwd: 'HIDDEN',
                userAccountControl: 'NON_EDITABLE',
                userPrincipalName: 'EDITABLE',
                streetAddress: 'EDITABLE',
                postalCode: 'EDITABLE',
                photo: 'EDITABLE',
                thumbnailPhoto: 'EDITABLE',
                memberOf: 'NON_EDITABLE',
                manager: 'NON_EDITABLE',
                createTimeStamp: 'READ_ONLY',
                modifyTimeStamp: 'READ_ONLY',

        ].sort{a,b-> a.key <=> b.key} as Map

        return output
    }

    @Override
    public Object lookupManagedSystemAttributes(Map<String, Object> binding) {

        def output = [
                'GROUP_MEMBERSHIP_ENABLED',
                'ON_DELETE',
                'INCLUDE_IN_PASSWORD_SYNC',
                'LOOKUP_USER_IN_OU'
        ] as List

        return output
    }

}
