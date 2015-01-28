package org.openiam.ui.webconsole.web.model;

import java.util.*;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;
import org.openiam.idm.srvc.res.dto.Resource;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ReconciliationConfigCommand {
    @JsonProperty("reconConfig")
    private ReconciliationConfig reconConfig;
    @JsonProperty("csvFileUpload")
    private CommonsMultipartFile csvFileUpload;
    @JsonProperty("CSVFileName")
    private String CSVFileName;
    @JsonProperty("isCSV")
    private boolean isCSV;

    @JsonProperty("resourceName")
    private String resourceName;
    @JsonProperty("resource")
    private Resource resource;
    @JsonProperty("reportType")
    private String reportType;

    @JsonProperty("useCustomScript")
    private boolean useCustomScript;


    private List<ReconciliationSituation> situationSet;

    public List<ReconciliationSituation> getSituationSet() {
        return situationSet;
    }

    public void setSituationSet(List<ReconciliationSituation> situationSet) {
        Collections.sort(situationSet, new Comparator<ReconciliationSituation>(){
            @Override
            public int compare(ReconciliationSituation o1, ReconciliationSituation o2) {
                return o1.getSituation().compareTo(o2.getSituation());
            }
        });
        this.situationSet = situationSet;
    }

    public ReconciliationConfig getReconConfig() {
        return reconConfig;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setReconConfig(ReconciliationConfig reconConfig) {
        this.reconConfig = reconConfig;
    }

    public CommonsMultipartFile getCsvFileUpload() {
        return csvFileUpload;
    }

    public void setCsvFileUpload(CommonsMultipartFile csvFileUpload) {
        this.csvFileUpload = csvFileUpload;
    }

    public String getCSVFileName() {
        return CSVFileName;
    }

    public void setCSVFileName(String cSVFileName) {
        CSVFileName = cSVFileName;
    }

    public boolean getIsCSV() {
        return isCSV;
    }

    public void setIsCSV(boolean isCSV) {
        this.isCSV = isCSV;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public boolean isUseCustomScript() {
        return useCustomScript;
    }

    public void setUseCustomScript(boolean useCustomScript) {
        this.useCustomScript = useCustomScript;
    }
}
