package org.openiam.ui.security.model;

import java.util.List;
import java.util.Map;

public class XSSPatternWrapper {
	
	public XSSPatternWrapper() {}

	private Map<String, XSSPatternRule> processors;

	public Map<String, XSSPatternRule> getProcessors() {
		return processors;
	}

	public void setProcessors(Map<String, XSSPatternRule> processors) {
		this.processors = processors;
	}
	
	
}
