package org.openiam.ui.rest.api.model;

import java.util.List;

/**
 * Created by: Alexander Duckardt
 * Date: 1/15/14.
 */
public class RoleSearchMetadata {
    private List<KeyNameBean> managedSystems;

    public RoleSearchMetadata() {}

    public List<KeyNameBean> getManagedSystems() {
        return managedSystems;
    }
    public void setManagedSystems(List<KeyNameBean> managedSystems) {
        this.managedSystems = managedSystems;
    }
}
