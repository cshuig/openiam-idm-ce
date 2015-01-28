package org.openiam.ui.webconsole.web.model;

import org.apache.commons.lang.StringUtils;
import org.openiam.idm.srvc.report.dto.ReportCriteriaParamDto;
import org.openiam.ui.web.model.AbstractBean;

public class ReportParamBean extends AbstractBean implements Comparable<ReportParamBean> {

	private String name;
	private String paramCaption;
    private String paramName;
	private String paramTypeId;
	private String paramTypeName;
    private String paramMetaTypeId;
    private String paramMetaTypeName;
    private Boolean isMultiple;
    private Boolean isRequired;
	private String reportId;
	private Integer displayOrder;
	private String requestParameters;
	
	public ReportParamBean() {
		super();
	}

	public ReportParamBean(ReportCriteriaParamDto paramDto) {
		setId(paramDto.getId());
        this.paramCaption = paramDto.getCaption();
		this.paramName = paramDto.getName();
		this.paramTypeId = paramDto.getTypeId();
		this.reportId = paramDto.getReportId();
		this.paramTypeName = paramDto.getTypeName();
        this.paramMetaTypeId = paramDto.getMetaTypeId();
        this.paramMetaTypeName = paramDto.getMetaTypeName();
        this.isMultiple = paramDto.getIsMultiple();
        this.isRequired = paramDto.getIsRequired();
		this.displayOrder = paramDto.getDisplayOrder();
		this.requestParameters = paramDto.getRequestParameters();
	}
	
	
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamTypeId() {
		return paramTypeId;
	}
	public void setParamTypeId(String paramTypeId) {
		this.paramTypeId = paramTypeId;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getParamTypeName() {
		return paramTypeName;
	}
	public void setParamTypeName(String paramTypeName) {
		this.paramTypeName = paramTypeName;
	}
    public Boolean getIsMultiple() {
        return isMultiple;
    }
    public void setIsMultiple(Boolean isMultiple) {
        this.isMultiple = isMultiple;
    }
    public Boolean getIsRequired() {
        return isRequired;
    }
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }
    public String getParamCaption() {
        return paramCaption;
    }
    public void setParamCaption(String paramCaption) {
        this.paramCaption = paramCaption;
    }
    public String getParamMetaTypeId() {
        return paramMetaTypeId;
    }
    public void setParamMetaTypeId(String paramMetaTypeId) {
        this.paramMetaTypeId = paramMetaTypeId;
    }
    public String getParamMetaTypeName() {
        return paramMetaTypeName;
    }
    public void setParamMetaTypeName(String paramMetaTypeName) {
        this.paramMetaTypeName = paramMetaTypeName;
    }
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getRequestParameters() {
		return requestParameters;
	}
	public void setRequestParameters(String requestParameters) {
		this.requestParameters = requestParameters;
	}


	@Override
	public int compareTo(ReportParamBean o) {
		return this.displayOrder.compareTo(o.displayOrder);
	}
}
