package org.openiam.ui.web.model;

import java.io.Serializable;

public abstract class PaginationCommand implements Serializable {

    private static final long serialVersionUID = 4652842287883439817L;
    protected Integer page;
    protected Integer size;
    protected Integer count;

    protected String[] pages = new String[]{"10","20","50","100","200","500"};
    protected static Integer defaultSize = 10;

    public Integer getPage() {
        return (page != null && page > 0) ? page : 0;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return (size != null && size > 0) ? size : 10;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public int getTotal() {
        int totalPages = 0;
        if(getCount() > getSize()) {
            totalPages = (getCount() / getSize()) + 1;
        } else if((getCount() % getSize()) == 0) {
            totalPages = getCount() / getSize();
        }
        return totalPages;
    }

    public Integer getCount() {
        return (count != null && count > 0) ? count : 0;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String[] getPages() {
        return pages;
    }

    public void setPages(String[] pages) {
        this.pages = pages;
    }
}
