package org.openiam.ui.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class OpeniamWebAuthenticationDetails extends WebAuthenticationDetails {

	private boolean identityQuestionsAnswered = false;
    private boolean usePolicyConfirmed = false;
	
	public OpeniamWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
	}

	public boolean isIdentityQuestionsAnswered() {
		return identityQuestionsAnswered;
	}

    public boolean isUsePolicyConfirmed() {
        return usePolicyConfirmed;
    }

	public void setIdentityQuestionsAnswered(boolean identityQuestionsAnswered) {
		this.identityQuestionsAnswered = identityQuestionsAnswered;
	}

    public void setUsePolicyConfirmed(boolean usePolicyConfirmed) {
        this.usePolicyConfirmed = usePolicyConfirmed;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (identityQuestionsAnswered ? 1231 : 1237);
        result = prime * result + (usePolicyConfirmed ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpeniamWebAuthenticationDetails other = (OpeniamWebAuthenticationDetails) obj;
		if (identityQuestionsAnswered != other.identityQuestionsAnswered)
			return false;
        if (usePolicyConfirmed != other.usePolicyConfirmed)
            return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("OpeniamWebAuthenticationDetails [identityQuestionsAnswered=%s, toString()=%s]",
						identityQuestionsAnswered, super.toString());
	}

	
}
