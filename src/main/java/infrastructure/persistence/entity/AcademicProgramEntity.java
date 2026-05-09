package infrastructure.persistence.entity;

import java.util.UUID;

public class AcademicProgramEntity {
    private UUID id;
    private InstituteEntity instituteEntity;
    private String name;

    public AcademicProgramEntity(UUID id, InstituteEntity instituteEntity, String name) {
        super();
        setId(id);
        setInstituteEntity(instituteEntity);
        setName(name);
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

    private void setId(UUID id) {
        this.id = id;
    }

    private void setInstituteEntity(InstituteEntity instituteEntity) {
        this.instituteEntity = instituteEntity;
    }

    private void setName(String name) {
        this.name = name;
    }
}
