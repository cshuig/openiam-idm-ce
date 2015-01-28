package org.openiam.ui.webconsole.web.model;

public class HistorySearchFormBean {
    private HistorySearchModel searchBean;
    private Integer pageNumber;
    private Integer size;

    public HistorySearchModel getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(HistorySearchModel searchBean) {
        this.searchBean = searchBean;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
