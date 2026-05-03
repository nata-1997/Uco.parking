package infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="AcademicProgram")
public class AcademicProgramEntity {

    @Id
    @Column(columnDefinition = "uniqueidentifier")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InstituteId", nullable = false)
    private InstituteEntity instituteEntity;

    @Column(name="Name")
    private String name;

    public AcademicProgramEntity(UUID id, InstituteEntity instituteEntity, String name) {
        super();
        generateId();
        this.instituteEntity = instituteEntity;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public InstituteEntity getInstituteEntity() {
        return instituteEntity;
    }

    public String getName() {
        return name;
    }

    private void generateId() {
        this.id = UUID.randomUUID();
    }

    private void setInstituteEntity(InstituteEntity instituteEntity) {
        this.instituteEntity = instituteEntity;
    }

    private void setName(String name) {
        this.name = name;
    }
}
