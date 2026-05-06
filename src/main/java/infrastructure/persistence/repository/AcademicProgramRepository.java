package infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

public interface AcademicProgramRepository {

    void create (AcademicProgramEntity entity);
    void update (AcademicProgramEntity entity);
    void delete (AcademicProgramEntity entity);
    AcademicProgramEntity findById(UUID id);
    List<AcademicProgramEntity> findByFilter(AcademicProgramEntity entity);
    List<AcademicProgramEntity> findAll();
}
