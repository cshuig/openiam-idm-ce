package org.openiam.ui.selfservice.web.groovy;

public class UnauthenticatedContactInfoPreprocessor extends AbstractContactInfoProcessor {

	public boolean isMiddleNameInputShown() {
		return true;
	}
	
	public boolean isMaidenNameInputShown() {
		return true;
	}
	
	public boolean isNickNameInputShown() {
		return true;
	}
	
	public boolean isDateOfBirthInputShown() {
		return true;
	}
	
	public boolean isFunctionalTitleInputShown() {
		return true;
	}
	
	public boolean isGenderInputShown() {
		return true;
	}
	
	public boolean isRoleInputShown() {
		return true;
	}
	
	public final boolean issupportContactInfoCreation() {
		return isSupportsEmailCreation() || isSupportsPhoneCreation() || isSupportsAddressCreation();
	}
	
	public boolean isSuppotsLoginCreation() {
		return true;
	}
}
