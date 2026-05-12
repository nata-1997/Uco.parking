package infrastructure.persistence.entity;

import crossscutting.helper.TextHelper;
import crossscutting.helper.UUIDHelper;

import java.util.UUID;

public class AcademicProgramEntity {
    private UUID id;
    private UUID instituteEntity;
    private String name;

    public AcademicProgramEntity(final UUID id, final UUID instituteEntity, final String name) {
        super();
        setId(id);
        setInstituteEntity(instituteEntity);
        setName(TextHelper.getDefaultWithTrim(name));
    }

    public UUID getId() {
        return id;
    }

    public UUID getInstituteEntity() {
        return instituteEntity;
    }

    public String getName() {
        return name;
    }

    private void setId(UUID id) {
        this.id = UUIDHelper.getUUIDHelper().getDefault(id);
    }

    private void setInstituteEntity(final UUID instituteEntity) {
        this.instituteEntity = instituteEntity;
    }

    private void setName(final String name) {
        this.name = TextHelper.getDefaultWithTrim(name);
    }
}
