package infrastructure.persistence.sql.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="AcademicProgram")
public class AcademicProgramJPAEntity {

    @Id
    @Column(name ="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "Institute")
    private InstituteJPAEntity instituteEntity;

    @Column(name="Name")
    private String name;

    public AcademicProgramJPAEntity() {
        super();
    }

    public AcademicProgramJPAEntity(UUID id, InstituteJPAEntity institute, String name) {
        super();
        setId(id);
        setName(name);
        setInstitute(institute);
    }

    public UUID getId() {
        return id;
    }

    public InstituteJPAEntity getInstituteEntity() {
        return instituteEntity;
    }

    public String getName() {
        return name;
    }

    private void setId (UUID id) {
        this.id = id;
    }

    public void setInstitute(InstituteJPAEntity institute) {
        this.instituteEntity = institute;
    }

    public void setName(String name) {
        this.name = name;
    }
}
