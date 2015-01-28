package org.openiam.ui.security.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class XSSPatternProcessor {
	
	private static Logger LOG = Logger.getLogger(XSSPatternProcessor.class);
	
	private String paramName;
	private String characterWhitelist;
	
	public XSSPatternProcessor() {
		
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getCharacterWhitelist() {
		return characterWhitelist;
	}

	public void setCharacterWhitelist(String characterWhitelist) {
		this.characterWhitelist = characterWhitelist;
	}

	public String process(final String value) {
		StringBuffer sb = null;
		if(value != null) {
			if(characterWhitelist != null) {
				for(int i = 0; i < value.length(); i++) {
					final String charAsString = new Character(value.charAt(i)).toString();
					if(charAsString.matches(characterWhitelist)) {
						if(sb == null) {
							sb = new StringBuffer();
						}
						sb.append(charAsString);
					} else {
						if(LOG.isDebugEnabled()) {
							LOG.debug(String.format("Character '%s' does not match regex '%s' at index '%s' of string '%s", charAsString, characterWhitelist, i, value));
						}
					}
				}
			}
		}
		return (sb != null) ? sb.toString() : null;
	}
	
	public String[] process(final String[] values) {
		String[] retVal = null;
		if(values != null) {
			retVal = new String[values.length];
			for(int i = 0; i < values.length; i++) {
				retVal[i] = process(values[i]);
			}
		}
		return retVal;
	}
}
