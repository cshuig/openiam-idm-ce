package org.openiam.idm.srvc.edu.course.dto;

import org.openiam.base.AttributeOperationEnum;
import org.openiam.dozer.DozerDTOCorrespondence;
import org.openiam.idm.srvc.edu.course.domain.CourseEntity;
import org.openiam.idm.srvc.edu.course.domain.CourseTermUserEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashSet;
import java.util.Set;

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
@XmlType(name = "Course", propOrder = {
        "id",
        "status",
        "name",
        "courseNumber",
        "districtId",
        "courseFolder",
        "courseAttributes",
        "selected",
        "operation",
        "schoolId",
        "schoolName",
        "districtName",
"programMembership",
"courseTerms",
"externalCourseId",
"courseTermUsers"}
)
@XmlRootElement(name = "Course")
@DozerDTOCorrespondence(CourseEntity.class)
public class Course implements java.io.Serializable {

    protected String id;

    protected String externalCourseId;

    protected String status;
    protected String name;

    protected String courseNumber;
    // organization
    protected String districtId;
    protected String schoolId;

    protected String courseFolder;

    protected Boolean selected = Boolean.FALSE;
    protected AttributeOperationEnum operation;

    protected Set<CourseAttribute> courseAttributes = new HashSet<CourseAttribute>();
    protected Set<Program> programMembership = new HashSet<Program>();

    protected Set<CourseTerm> courseTerms = new HashSet<CourseTerm>();
    protected Set<CourseTermUserEntity> courseTermUsers = new HashSet<CourseTermUserEntity>();

    protected String schoolName;
    protected String districtName;


    // Constructors

    /**
     * default constructor
     */
    public Course() {
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

    public Set<CourseTerm> getCourseTerms() {
        return courseTerms;
    }

    public void setCourseTerms(Set<CourseTerm> courseTerms) {
        this.courseTerms = courseTerms;
    }

    public String getExternalCourseId() {
        return externalCourseId;
    }

    public void setExternalCourseId(String externalCourseId) {
        this.externalCourseId = externalCourseId;
    }

    public Set<CourseTermUserEntity> getCourseTermUsers() {
        return courseTermUsers;
    }

    public void setCourseTermUsers(Set<CourseTermUserEntity> courseTermUsers) {
        this.courseTermUsers = courseTermUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

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
