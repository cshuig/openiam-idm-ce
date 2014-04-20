package org.openiam.idm.srvc.edu.course.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.BaseObject;
import org.openiam.dozer.DozerDTOCorrespondence;
import org.openiam.idm.srvc.edu.course.dto.Program;

import javax.persistence.*;


@Entity
@Table(name = "PROGRAM")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DozerDTOCorrespondence(Program.class)
public class ProgramEntity extends BaseObject implements java.io.Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name="PROGRAM_ID", length=32)
    protected String id;

    @Column(name="STATUS",length=20)
    protected String status;

    @Column(name="NAME",length=60)
    protected String name;

    @Transient
    protected AttributeOperationEnum operation;



    // Constructors

    /**
     * default constructor
     */
    public ProgramEntity() {
    }

    public ProgramEntity(String name) {

        this.name = name;
        this.id = null;
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



    public AttributeOperationEnum getOperation() {
        return operation;
    }

    public void setOperation(AttributeOperationEnum operation) {
        this.operation = operation;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgramEntity)) return false;

        ProgramEntity program = (ProgramEntity) o;

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
