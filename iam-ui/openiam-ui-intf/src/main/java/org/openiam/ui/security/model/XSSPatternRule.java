package org.openiam.ui.security.model;

import java.util.List;
import java.util.Map;

public class XSSPatternRule {

	private boolean ignoreXSS;
	private Map<String, XSSPatternProcessor> rules;
	
	public XSSPatternRule() {}

	public Map<String, XSSPatternProcessor> getRules() {
		return rules;
	}

	public void setRules(Map<String, XSSPatternProcessor> rules) {
		this.rules = rules;
	}

	public boolean isIgnoreXSS() {
		return ignoreXSS;
	}

	public void setIgnoreXSS(boolean ignoreXSS) {
		this.ignoreXSS = ignoreXSS;
	}

	
}
