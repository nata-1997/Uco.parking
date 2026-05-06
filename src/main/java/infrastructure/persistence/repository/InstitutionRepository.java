package infrastructure.persistence.repository;

import infrastructure.persistence.entity.InstituteEntity;

import java.util.List;
import java.util.UUID;

public interface InstitutionRepository {

    void create (InstituteEntity entity);
    void update (InstituteEntity entity);
    void delete (InstituteEntity entity);
    InstituteEntity findById(UUID id);
    List<InstituteEntity> findByFilter(InstituteEntity entity);
    List<InstituteEntity> findAll();
}
