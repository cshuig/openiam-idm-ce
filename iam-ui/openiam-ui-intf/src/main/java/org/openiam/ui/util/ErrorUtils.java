package org.openiam.ui.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.base.ws.Response;
import org.openiam.exception.EsbErrorToken;
import org.openiam.ui.util.messages.ErrorToken;

public class ErrorUtils {

	public static List<ErrorToken> getESBErrorTokens(final Response response) {
		final List<ErrorToken> retVal = new LinkedList<ErrorToken>();
		if(CollectionUtils.isNotEmpty(response.getErrorTokenList())) {
			for (final EsbErrorToken error: response.getErrorTokenList()){
                final ErrorToken errorToken = new ErrorToken();
                errorToken.setValidationError(error.getMessage());
                if(error.getLengthConstraint()!=null) {
                    errorToken.setParams(new Object[]{error.getLengthConstraint()});
                }
                retVal.add(errorToken);
            }
		}
		return retVal;
	}
}
