package org.openiam.idm.srvc.edu.course.dto;

import org.openiam.base.AttributeOperationEnum;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
        "operation"}
)
public class Course implements java.io.Serializable {

    protected String id;
    protected String status;
    protected String name;

    protected String courseNumber;
    // organization
    protected String districtId;
    protected String courseFolder;

    protected Boolean selected = Boolean.FALSE;
    protected AttributeOperationEnum operation;

    protected Set<CourseAttribute> courseAttributes = new HashSet<CourseAttribute>(0);



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
                ", courseFolder='" + courseFolder + '\'' +
                ", selected=" + selected +
                ", operation=" + operation +
                ", courseAttributes=" + courseAttributes +
                '}';
    }
}
