package org.openiam.ui.web.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.openiam.ui.util.TreeNode;

import java.util.List;

/**
 * Created by: Alexander Duckardt
 * Date: 12/31/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TreeResponse<Bean extends AbstractBean> {
    public static final TreeResponse<AbstractBean> EMPTY_RESPONSE = new TreeResponse<AbstractBean>();
    static {
        EMPTY_RESPONSE.setEmptySearchBean(true);
    }

    private String error;
    private int size;
    private List<TreeNode<Bean>> beans;
    private boolean isEmptySearchBean;

    public TreeResponse(){}

    public TreeResponse(final List<TreeNode<Bean>> beans, final int size) {
        this.size = size;
        this.beans = beans;
    }

    public int getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<TreeNode<Bean>> getBeans() {
        return beans;
    }

    public void setBeans(List<TreeNode<Bean>> beans) {
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
