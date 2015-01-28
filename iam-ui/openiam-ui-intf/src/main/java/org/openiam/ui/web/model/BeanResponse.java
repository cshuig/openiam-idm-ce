package org.openiam.ui.web.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeanResponse {
    public static final BeanResponse EMPTY_RESPONSE = new BeanResponse();
    static {
        EMPTY_RESPONSE.setEmptySearchBean(true);
    }

    private String error;
	private Integer size;
	private List<? extends AbstractBean> beans;
    private boolean isEmptySearchBean;

    public BeanResponse(){}

	public BeanResponse(final List<? extends AbstractBean> beans, final Integer size) {
		this.size = size;
		this.beans = beans;
	}

	public Integer getSize() {
		return size;
	}

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<? extends AbstractBean> getBeans() {
		return beans;
	}

    public void setBeans(List<? extends AbstractBean> beans) {
        this.beans=beans;
    }

    public boolean isEmptySearchBean() {
        return isEmptySearchBean;
    }

    public void setEmptySearchBean(boolean isEmptySearchBean) {
        this.isEmptySearchBean = isEmptySearchBean;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
