package org.openiam.ui.rest.api.model;

public class AuditLogSearchForm {

    private String userId;
    private String requestorId;
    private String requestorLogin;
    private String targetId;
    private Long fromDate;
    private Long toDate;
    private int from;
    private int size;
    private int page;
    private int totalSize;
    private String managedSystem;
    private String targetType;
    private String action;
    private String result;
    private String secondaryTargetType;
    private String secondaryTargetId;
    private String source;
    private String sourceId;
    private boolean showChildren;

    public AuditLogSearchForm() {
    }

    public boolean isShowChildren() {
        return showChildren;
    }

    public void setShowChildren(boolean showChildren) {
        this.showChildren = showChildren;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public String getRequestorLogin() {
        return requestorLogin;
    }

    public void setRequestorLogin(String requestorLogin) {
        this.requestorLogin = requestorLogin;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Long getFromDate() {
        return fromDate;
    }

    public void setFromDate(Long fromDate) {
        this.fromDate = fromDate;
    }

    public Long getToDate() {
        return toDate;
    }

    public void setToDate(Long toDate) {
        this.toDate = toDate;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public String getManagedSystem() {
        return managedSystem;
    }

    public void setManagedSystem(String managedSystem) {
        this.managedSystem = managedSystem;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSecondaryTargetType() {
        return secondaryTargetType;
    }

    public void setSecondaryTargetType(String secondaryTargetType) {
        this.secondaryTargetType = secondaryTargetType;
    }

    public String getSecondaryTargetId() {
        return secondaryTargetId;
    }

    public void setSecondaryTargetId(String secondaryTargetId) {
        this.secondaryTargetId = secondaryTargetId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
