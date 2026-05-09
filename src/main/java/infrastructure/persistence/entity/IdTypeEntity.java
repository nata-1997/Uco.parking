package infrastructure.persistence.entity;

import infrastructure.persistence.crossscutting.Helper.TextHelper;
import infrastructure.persistence.crossscutting.Helper.UUIDHelper;

import java.util.UUID;

public class IdTypeEntity {
    private UUID id;
    private String name;

    public IdTypeEntity(UUID id, String name) {
        super();
        setId(id);
        setName(name);
    }

    public IdTypeEntity(){
        setId(UUIDHelper.getUUIDHelper().getDefault());
        setName(TextHelper.getDefault());
    }

    public IdTypeEntity(UUID id){
        setId(id);
        setName(TextHelper.getDefault());
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = UUIDHelper.getUUIDHelper().getDefault(id);
    }

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = TextHelper.getDefaultWithTrim(name);
    }
}
