package org.openiam.ui.webconsole.am.web.model;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class AuthLevelAttributeFormRequest {

	private String name;
	private String id;
	private String groupingId;
	private String typeId;
	private String valueAsString;
	private boolean modifiedFile;
	private boolean hasFile;
	private CommonsMultipartFile bytes;
	
	public AuthLevelAttributeFormRequest() {
		
	}
	
	public boolean isModifiedFile() {
		return modifiedFile;
	}
	public void setModifiedFile(boolean modifiedFile) {
		this.modifiedFile = modifiedFile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupingId() {
		return groupingId;
	}
	public void setGroupingId(String groupingId) {
		this.groupingId = groupingId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getValueAsString() {
		return valueAsString;
	}
	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
	}
	public CommonsMultipartFile getBytes() {
		return bytes;
	}
	public void setBytes(CommonsMultipartFile bytes) {
		this.bytes = bytes;
	}
	public boolean isHasFile() {
		if(bytes != null && ArrayUtils.isNotEmpty(bytes.getBytes())) {
			setHasFile(true);
		}
		return hasFile;
	}

	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}
}
