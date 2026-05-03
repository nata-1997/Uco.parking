package infrastructure.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name="Institute")
public class InstituteEntity {

    @Id
    @Column(columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "Name")
    private String name;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
