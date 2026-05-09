package infrastructure.persistence.entity;

import crossscutting.helper.ObjectHelper;
import crossscutting.helper.TextHelper;
import crossscutting.helper.UUIDHelper;

import java.util.UUID;

public class AcademicProgramEntity {
    private UUID id;
    private InstituteEntity instituteEntity;
    private String name;


    public AcademicProgramEntity() {
        setId(UUIDHelper.getUUIDHelper().getDefault());
        setInstituteEntity(new InstituteEntity());
        setName(TextHelper.getDefault());
    }

    public AcademicProgramEntity(final UUID id) {
        setId(id);
        setInstituteEntity(new InstituteEntity());
        setName(TextHelper.getDefault());
    }

    public AcademicProgramEntity(final UUID id, final InstituteEntity instituteEntity, final String name) {
        super();
        setId(id);
        setInstituteEntity(instituteEntity);
        setName(TextHelper.getDefaultWithTrim(name));
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
        this.id = UUIDHelper.getUUIDHelper().getDefault(id);
    }

    private void setInstituteEntity(final InstituteEntity instituteEntity) {
        this.instituteEntity = ObjectHelper.getDefault(instituteEntity, new InstituteEntity());
    }

    private void setName(final String name) {
        this.name = TextHelper.getDefaultWithTrim(name);
    }
}
