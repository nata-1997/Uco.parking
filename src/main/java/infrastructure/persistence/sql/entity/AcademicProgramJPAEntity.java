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

    public AcademicProgramJPAEntity(UUID id, InstituteJPAEntity institute, String name) {
        super();
        generateId();
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

    private void generateId() {
        this.id = UUID.randomUUID();
    }

    private void setInstitute(InstituteJPAEntity institute) {
        this.instituteEntity = institute;
    }

    private void setName(String name) {
        this.name = name;
    }
}
