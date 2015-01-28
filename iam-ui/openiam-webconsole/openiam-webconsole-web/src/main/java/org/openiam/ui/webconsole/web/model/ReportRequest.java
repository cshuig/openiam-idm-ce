package org.openiam.ui.webconsole.web.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ReportRequest {
	@JsonProperty("reportId")
    private String reportId;
	@JsonProperty("reportName")
    private String reportName;
	@JsonProperty("resourceId")
	private String resourceId;
	@JsonProperty("reportDataSourceName")
    private String reportDataSourceName;
    @JsonProperty("reportDataSourceFile")
    private CommonsMultipartFile reportDataSourceFile;
    @JsonProperty("newDataSourceFile")
    private Boolean newDataSourceFile;
    @JsonProperty("overwriteDataSourceFile")
    private Boolean overwriteDataSourceFile;
    @JsonProperty("reportDesignName")
    private String reportDesignName;
	@JsonProperty("reportDesignFile")
    private CommonsMultipartFile reportDesignFile;
    @JsonProperty("newDesignFile")
    private Boolean newDesignFile;
    @JsonProperty("overwriteDesignFile")
    private Boolean overwriteDesignFile;

	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getReportDataSourceName() {
		return reportDataSourceName;
	}
	public void setReportDataSourceName(String reportDataSourceName) {
		this.reportDataSourceName = reportDataSourceName;
	}
	public CommonsMultipartFile getReportDesignFile() {
		return reportDesignFile;
	}
	public void setReportDesignFile(CommonsMultipartFile reportDesignFile) {
		this.reportDesignFile = reportDesignFile;
	}
    
    public String getReportDesignName() {
        return reportDesignName;
    }

    public void setReportDesignName(String reportDesignName) {
        this.reportDesignName = reportDesignName;
    }

    public Boolean getNewDesignFile() {
        return newDesignFile == null ? false : newDesignFile;
    }

    public void setNewDesignFile(Boolean newDesignFile) {
        this.newDesignFile = newDesignFile;
    }

    public Boolean getOverwriteDesignFile() {
        return overwriteDesignFile;
    }

    public void setOverwriteDesignFile(Boolean overwriteDesignFile) {
        this.overwriteDesignFile = overwriteDesignFile;
    }

    public CommonsMultipartFile getReportDataSourceFile() {
        return reportDataSourceFile;
    }

    public void setReportDataSourceFile(CommonsMultipartFile reportDataSourceFile) {
        this.reportDataSourceFile = reportDataSourceFile;
    }

    public Boolean getNewDataSourceFile() {
        return newDataSourceFile == null ? false : newDataSourceFile;
    }

    public void setNewDataSourceFile(Boolean newDataSourceFile) {
        this.newDataSourceFile = newDataSourceFile;
    }

    public Boolean getOverwriteDataSourceFile() {
        return overwriteDataSourceFile;
    }

    public void setOverwriteDataSourceFile(Boolean overwriteDataSourceFile) {
        this.overwriteDataSourceFile = overwriteDataSourceFile;
    }


}
