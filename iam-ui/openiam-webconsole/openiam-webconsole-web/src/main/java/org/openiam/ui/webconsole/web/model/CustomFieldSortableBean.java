package org.openiam.ui.webconsole.web.model;

public class CustomFieldSortableBean extends CustomFieldBean implements Comparable<CustomFieldSortableBean> {
    private Integer displayOrder;


    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public int compareTo(CustomFieldSortableBean o) {
        return this.displayOrder.compareTo(o.displayOrder);
    }
}
