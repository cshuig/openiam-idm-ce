package org.openiam.idm.srvc.edu.course.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.dozer.DozerDTOCorrespondence;
import org.openiam.idm.srvc.edu.course.dto.Course;
import org.openiam.idm.srvc.edu.course.dto.CourseAttribute;
import org.openiam.idm.srvc.edu.course.dto.Program;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COURSE")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DozerDTOCorrespondence(Course.class)
public class CourseEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name="COURSE_ID", length=32)
    protected String id;

    @Column(name="STATUS",length=20)
    protected String status;

    @Column(name="NAME",length=60)
    protected String name;

    @Column(name="COURSE_NUMBER",length=60)
    protected String courseNumber;

    // organization
    @Column(name="DISTRICT_ID",length=32)
    protected String districtId;

    @Column(name="SCHOOL_ID",length=32)
    protected String schoolId;

    @Column(name="COURSE_FOLDER",length=255)
    protected String courseFolder;

    @Transient
    protected Boolean selected = Boolean.FALSE;
    @Transient
    protected AttributeOperationEnum operation;

    @Transient
    protected Set<CourseAttribute> courseAttributes = new HashSet<CourseAttribute>();
    @Transient
    protected Set<Program> programMembership = new HashSet<Program>();

    @OneToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinColumn(name="COURSE_ID", referencedColumnName="COURSE_ID")
    @MapKeyColumn(name="termId")
    protected Set<CourseTermEntity> courseTerms = new HashSet<CourseTermEntity>();

    @Transient
    protected String schoolName;
    @Transient
    protected String districtName;


    // Constructors

    /**
     * default constructor
     */
    public CourseEntity() {
    }

    public Set<CourseAttribute> getCourseAttributes() {
        return courseAttributes;
    }

    public void setCourseAttributes(Set<CourseAttribute> courseAttributes) {
        this.courseAttributes = courseAttributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }


    public String getCourseFolder() {
        return courseFolder;
    }

    public void setCourseFolder(String courseFolder) {
        this.courseFolder = courseFolder;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public AttributeOperationEnum getOperation() {
        return operation;
    }

    public void setOperation(AttributeOperationEnum operation) {
        this.operation = operation;
    }


    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Set<Program> getProgramMembership() {
        return programMembership;
    }

    public void setProgramMembership(Set<Program> programMembership) {
        this.programMembership = programMembership;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseEntity)) return false;

        CourseEntity course = (CourseEntity) o;

        if (courseFolder != null ? !courseFolder.equals(course.courseFolder) : course.courseFolder != null)
            return false;
        if (courseNumber != null ? !courseNumber.equals(course.courseNumber) : course.courseNumber != null)
            return false;
        if (districtId != null ? !districtId.equals(course.districtId) : course.districtId != null) return false;
        if (id != null ? !id.equals(course.id) : course.id != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (schoolId != null ? !schoolId.equals(course.schoolId) : course.schoolId != null) return false;
        if (selected != null ? !selected.equals(course.selected) : course.selected != null) return false;
        if (status != null ? !status.equals(course.status) : course.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (courseNumber != null ? courseNumber.hashCode() : 0);
        result = 31 * result + (districtId != null ? districtId.hashCode() : 0);
        result = 31 * result + (schoolId != null ? schoolId.hashCode() : 0);
        result = 31 * result + (courseFolder != null ? courseFolder.hashCode() : 0);
        result = 31 * result + (selected != null ? selected.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", districtId='" + districtId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", courseFolder='" + courseFolder + '\'' +
                ", selected=" + selected +
                ", operation=" + operation +
                ", courseAttributes=" + courseAttributes +
                ", schoolName='" + schoolName + '\'' +
                ", districtName='" + districtName + '\'' +
                '}';
    }
}
