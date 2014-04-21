package org.openiam.idm.srvc.edu.course.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.openiam.base.BaseObject;
import org.openiam.dozer.DozerDTOCorrespondence;
import org.openiam.idm.srvc.edu.course.dto.term.Term;

import javax.persistence.*;

@Entity
@Table(name = "TERM")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DozerDTOCorrespondence(Term.class)
public class TermEntity extends BaseObject implements java.io.Serializable   {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name="TERM_ID", length=32)
    protected String id;

    @Column(name="NAME",length=60)
    protected String name;

    @Column(name="SCHOOL_YEAR",length=60)
    protected String schoolYear;

    @Column(name="PERIOD_DESCRIPTION",length=255)
    protected String description;

    @Column(name="DISTRICT_ID",length=32)
    protected String districtId;

    public TermEntity() {
    }

    public TermEntity(String id, String name, String districtId) {
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
        if (!(o instanceof TermEntity)) return false;

        TermEntity term = (TermEntity) o;

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
