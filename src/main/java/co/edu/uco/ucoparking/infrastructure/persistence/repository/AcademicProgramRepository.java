package co.edu.uco.ucoparking.infrastructure.persistence.repository;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.AcademicProgramEntity;

import java.util.List;
import java.util.UUID;

public interface AcademicProgramRepository {

    void create (AcademicProgramEntity entity);
    void update (AcademicProgramEntity entity);
    void delete (UUID id);
    AcademicProgramEntity findById(UUID id);
    List<AcademicProgramEntity> findByFilter(AcademicProgramEntity entity);
    List<AcademicProgramEntity> findAll();
}
