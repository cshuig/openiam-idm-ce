import org.openiam.idm.srvc.mngsys.service.AbstractAttributeNamesLookupScript

public class SynchLDAPAttributeNamesLookup extends AbstractAttributeNamesLookupScript {

    @Override
    public Object lookupPolicyMapAttributes(Map<String, Object> binding) {

        def output = [
                '*',
                'modifyTimestamp',
                'createTimestamp'
        ] as List

        return output
    }

}
