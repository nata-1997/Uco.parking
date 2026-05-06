package infrastructure.persistence.repository;

import infrastructure.persistence.entity.InstituteEntity;

import java.util.List;
import java.util.UUID;

public interface InstituteRepository {

    void create (InstituteEntity entity);
    void update (InstituteEntity entity);
    void delete (UUID id);
    InstituteEntity findById(UUID id);
    List<InstituteEntity> findByFilter(InstituteEntity entity);
    List<InstituteEntity> findAll();
}
