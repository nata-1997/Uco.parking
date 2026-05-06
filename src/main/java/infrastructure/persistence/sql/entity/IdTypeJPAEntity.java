package infrastructure.persistence.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;


@Entity
@Table(name="IdType")
public class IdTypeJPAEntity {

    @Id
    @Column(columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
