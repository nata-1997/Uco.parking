package co.edu.uco.ucoparking.infrastructure.persistence.sql.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name="Institute")
public class InstituteJPAEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "Name")
    private String name;

    protected InstituteJPAEntity() {
    }

    public InstituteJPAEntity(UUID id, String name) {
        super();
        setId(id);
        setName(name);
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
