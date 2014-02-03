package org.openiam.selfsrvc.edu.common.course;

import org.openiam.idm.srvc.org.dto.Organization;

import java.io.Serializable;
import java.util.*;

/**
 * Command object for the SelectCourses
 * @author suneet
 *
 */
public class TermCommand implements Serializable {
	 
    private String districtId;
    private List<Organization> districtList;

    public TermCommand() {
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public List<Organization> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<Organization> districtList) {
        this.districtList = districtList;
    }
}
