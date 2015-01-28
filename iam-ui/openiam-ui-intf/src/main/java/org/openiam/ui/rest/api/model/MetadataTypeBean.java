package org.openiam.ui.rest.api.model;

import org.openiam.idm.srvc.meta.domain.MetadataTypeGrouping;

public class MetadataTypeBean extends KeyNameBean {
	
	public MetadataTypeBean() {
		super();
	}

	private MetadataTypeGrouping grouping;

	public MetadataTypeGrouping getGrouping() {
		return grouping;
	}

	public void setGrouping(MetadataTypeGrouping grouping) {
		this.grouping = grouping;
	}
	
	
}
