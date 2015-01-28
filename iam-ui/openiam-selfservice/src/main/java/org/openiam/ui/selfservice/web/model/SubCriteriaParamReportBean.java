package org.openiam.ui.selfservice.web.model;


import org.openiam.ui.rest.api.model.KeyNameBean;

public class SubCriteriaParamReportBean extends KeyNameBean {
	private String rscpId;
   private String id;
	private String name;
	private String value;
	private String type;
	private String reportId;
	
	
	public SubCriteriaParamReportBean(){
		super();
	}
	
	public SubCriteriaParamReportBean(String rscpId,String id, String reportId, String name, String value, String type){
		setRscpId(rscpId);
		this.id=id;
		this.name=name;
		this.reportId=reportId;
		this.type=type;
		this.value=value;	
		
	}
	

	
	
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
		
	@Override
	public String toString() {
		return "SubscribeReportBean [name=" + name + ", reportId="
				+ reportId + ",value=" + value + ", type=" + type + ",id=" + id + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRscpId() {
		return rscpId;
	}

	public void setRscpId(String rscpId) {
		this.rscpId = rscpId;
	}

	
	
	
	
	
	
}
