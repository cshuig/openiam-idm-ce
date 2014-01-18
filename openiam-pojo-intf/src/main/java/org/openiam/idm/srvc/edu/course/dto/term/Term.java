package org.openiam.idm.srvc.edu.course.dto.term;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p/>
 * Java class for Program complex type.
 * <p/>
 * <p/>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p/>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Term", propOrder = {
        "id",
        "name",
        "schoolYear",
        "districtId",
        "description"}
)
public class Term implements java.io.Serializable {

    protected String id;
    protected String name;

    protected String schoolYear;
    protected String description;
    protected String districtId;

    public Term() {
    }

    public Term(String id, String name, String districtId) {
        this.id = id;
        this.name = name;
        this.districtId = districtId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    @Override
    public String toString() {
        return "Term{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", schoolYear='" + schoolYear + '\'' +
                ", description='" + description + '\'' +
                ", districtId='" + districtId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Term)) return false;

        Term term = (Term) o;

        if (description != null ? !description.equals(term.description) : term.description != null) return false;
        if (districtId != null ? !districtId.equals(term.districtId) : term.districtId != null) return false;
        if (id != null ? !id.equals(term.id) : term.id != null) return false;
        if (name != null ? !name.equals(term.name) : term.name != null) return false;
        if (schoolYear != null ? !schoolYear.equals(term.schoolYear) : term.schoolYear != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (schoolYear != null ? schoolYear.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (districtId != null ? districtId.hashCode() : 0);
        return result;
    }
}
