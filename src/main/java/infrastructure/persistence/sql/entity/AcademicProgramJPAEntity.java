package infrastructure.persistence.sql.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="AcademicProgram")
public class AcademicProgramJPAEntity {

    @Id
    @Column(columnDefinition = "uniqueidentifier")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Institute", nullable = false)
    private InstituteJPAEntity instituteEntity;

    @Column(name="Name")
    private String name;

    public AcademicProgramJPAEntity(UUID id, InstituteJPAEntity instituteEntity, String name) {
        super();
        generateId();
        this.instituteEntity = instituteEntity;
        this.name = name;
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

    private void setInstituteEntity(InstituteJPAEntity instituteEntity) {
        this.instituteEntity = instituteEntity;
    }

    private void setName(String name) {
        this.name = name;
    }
}
