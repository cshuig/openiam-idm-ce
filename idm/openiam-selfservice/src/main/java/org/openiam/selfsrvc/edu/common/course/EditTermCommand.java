package org.openiam.selfsrvc.edu.common.course;

import org.openiam.idm.srvc.edu.course.dto.term.Term;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Command object for the SelectCourses
 * @author suneet
 *
 */
public class EditTermCommand implements Serializable {

    private String districtId;
    private List<Term> termList = new ArrayList<Term>();

    public EditTermCommand() {
    }

    public List<Term> getTermList() {
        return termList;
    }

    public void setTermList(List<Term> termList) {
        this.termList = termList;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }
}
