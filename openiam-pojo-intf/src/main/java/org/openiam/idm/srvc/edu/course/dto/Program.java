package org.openiam.idm.srvc.edu.course.dto;

import org.openiam.base.AttributeOperationEnum;

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
@XmlType(name = "Program", propOrder = {
        "id",
        "status",
        "name",
        "selected",
        "operation"}
)
public class Program implements java.io.Serializable {

    protected String id;

    protected String status;

    protected String name;

    protected Boolean selected = Boolean.FALSE;
    protected AttributeOperationEnum operation;



    // Constructors

    /**
     * default constructor
     */
    public Program() {
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
        if (!(o instanceof Program)) return false;

        Program program = (Program) o;

        if (id != null ? !id.equals(program.id) : program.id != null) return false;
        if (name != null ? !name.equals(program.name) : program.name != null) return false;
        if (operation != program.operation) return false;
        if (selected != null ? !selected.equals(program.selected) : program.selected != null) return false;
        if (status != null ? !status.equals(program.status) : program.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (selected != null ? selected.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
    }
}
