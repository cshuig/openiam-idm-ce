import org.openiam.idm.srvc.mngsys.service.AbstractAttributeNamesLookupScript

public class OracleAttributeNamesLookup extends AbstractAttributeNamesLookupScript {

    @Override
    public Object lookupPolicyMapAttributes(Map<String, Object> binding) {

        def output = [
                'PASSWORD': 'HIDDEN',
                'USERNAME': 'NON_EDITABLE',
                'ACCOUNT_STATUS': 'READ_ONLY',
                'LOCK_DATE': 'READ_ONLY'
        ].sort{a,b-> a.key <=> b.key} as Map

        return output
    }
}
