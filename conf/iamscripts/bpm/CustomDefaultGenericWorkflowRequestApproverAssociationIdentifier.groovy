package bpm

import java.util.Set;

import org.openiam.bpm.activiti.groovy.DefaultGenericWorkflowRequestApproverAssociationIdentifier;
import org.openiam.bpm.request.GenericWorkflowRequest;

class CustomDefaultGenericWorkflowRequestApproverAssociationIdentifier extends DefaultGenericWorkflowRequestApproverAssociationIdentifier {

	@Override
	public void calculateApprovers() {
		super.calculateApprovers();
	}
	
	@Override
	protected boolean isRequestForEntityCreation(final GenericWorkflowRequest request) {
		return super.isRequestForEntityCreation(request);
	}
	
	@Override
	protected Set<String> getApproversForEntityCreation(final GenericWorkflowRequest request) {
		return super.getApproversForEntityCreation(request);
	}
}
