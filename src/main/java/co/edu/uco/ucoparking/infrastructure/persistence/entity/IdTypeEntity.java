package co.edu.uco.ucoparking.infrastructure.persistence.entity;

import co.edu.uco.ucoparking.crossscutting.helper.TextHelper;
import co.edu.uco.ucoparking.crossscutting.helper.UUIDHelper;

import java.util.UUID;

public class IdTypeEntity {
    private UUID id;
    private String name;

    public IdTypeEntity(final UUID id, final String name) {
        super();
        setId(id);
        setName(name);
    }

    public IdTypeEntity(){
        setId(UUIDHelper.getUUIDHelper().getDefault());
        setName(TextHelper.getDefault());
    }

    public IdTypeEntity(final UUID id){
        setId(id);
        setName(TextHelper.getDefault());
    }

    public UUID getId() {
        return id;
    }

    private void setId(final UUID id) {
        this.id = UUIDHelper.getUUIDHelper().getDefault(id);
    }

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = TextHelper.getDefaultWithTrim(name);
    }
}
