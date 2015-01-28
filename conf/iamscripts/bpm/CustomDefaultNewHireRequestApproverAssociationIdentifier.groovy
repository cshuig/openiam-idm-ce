package bpm
import org.openiam.bpm.activiti.groovy.DefaultNewHireRequestApproverAssociationIdentifier;

class CustomDefaultNewHireRequestApproverAssociationIdentifier extends DefaultNewHireRequestApproverAssociationIdentifier {

	@Override
	public void calculateApprovers() {
		super.calculateApprovers();
	}
	
	@Override
	protected boolean isLimitToSingleApprover() {
		return super.isLimitToSingleApprover();
	}
}
