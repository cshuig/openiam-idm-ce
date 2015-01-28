package org.openiam.ui.rest.api.model;

import java.util.List;

public class MetadataAttributeBean extends KeyNameValueBean {
	
	private String parentId;
	private String metadataId;
	private String metadataName;
	private List<String> values;
	protected Boolean isMultivalued;
	
	public MetadataAttributeBean() {
		
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMetadataId() {
		return metadataId;
	}

	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}

	public String getMetadataName() {
		return metadataName;
	}

	public void setMetadataName(String metadataName) {
		this.metadataName = metadataName;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public Boolean getIsMultivalued() {
		return isMultivalued;
	}

	public void setIsMultivalued(Boolean isMultivalued) {
		this.isMultivalued = isMultivalued;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((isMultivalued == null) ? 0 : isMultivalued.hashCode());
		result = prime * result
				+ ((metadataId == null) ? 0 : metadataId.hashCode());
		result = prime * result
				+ ((metadataName == null) ? 0 : metadataName.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		MetadataAttributeBean other = (MetadataAttributeBean) obj;
		if (isMultivalued == null) {
			if (other.isMultivalued != null)
				return false;
		} else if (!isMultivalued.equals(other.isMultivalued))
			return false;
		if (metadataId == null) {
			if (other.metadataId != null)
				return false;
		} else if (!metadataId.equals(other.metadataId))
			return false;
		if (metadataName == null) {
			if (other.metadataName != null)
				return false;
		} else if (!metadataName.equals(other.metadataName))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("MetadataAttributeBean [parentId=%s, metadataId=%s, metadataName=%s, values=%s, isMultivalued=%s]",
						parentId, metadataId, metadataName, values,
						isMultivalued);
	}
	
	

	
	
}
