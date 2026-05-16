package co.edu.uco.ucoparking.infrastructure.persistence.sql;


import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.StudentJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentJPAEntity, UUID> {

}
