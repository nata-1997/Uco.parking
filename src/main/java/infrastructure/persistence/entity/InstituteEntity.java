package infrastructure.persistence.entity;

import crossscutting.helper.TextHelper;
import crossscutting.helper.UUIDHelper;

import java.util.UUID;

public class InstituteEntity {
    private UUID id;
    private String name;

    public InstituteEntity() {
        setId(UUIDHelper.getUUIDHelper().getDefault());
    }

    public InstituteEntity(final UUID id) {
        setId(id);
        setName(TextHelper.getDefault());
    }

    public InstituteEntity(final UUID id, final String name) {
        super();
        setId(id);
        setName(TextHelper.getDefaultWithTrim(name));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void setId(UUID id) {
        this.id = UUIDHelper.getUUIDHelper().getDefault(id);
    }

    private void setName(String name) {
        this.name = TextHelper.getDefaultWithTrim(name);
    }
}
