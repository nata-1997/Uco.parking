package infrastructure.persistence.entity;

import java.util.UUID;

public class IdTypeEntity {
    private UUID id;
    private String name;

    public IdTypeEntity(UUID id, String name) {
        super();
        setId(id);
        setName(name);
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
