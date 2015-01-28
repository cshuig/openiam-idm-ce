package org.openiam.ui.rest.api.model;

import org.openiam.ui.web.model.AbstractBean;

public class KeyNameBean extends AbstractBean implements Comparable<KeyNameBean>{

	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    public KeyNameBean() {

    }
    public KeyNameBean(String id, String name) {
        setId(id);
        this.name = name;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyNameBean other = (KeyNameBean) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

    @Override
    public int compareTo(KeyNameBean o) {
        if (this.getName() == null) {
            return o.getName() == null ? 0 : -1;
        } else if (o.getName() == null) {
            return 1;
        } else {
            return this.getName().compareToIgnoreCase(o.getName());
        }
    }
}
