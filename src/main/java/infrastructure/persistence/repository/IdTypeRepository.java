package infrastructure.persistence.repository;

import infrastructure.persistence.entity.IdTypeEntity;

import java.util.List;
import java.util.UUID;

public interface IdTypeRepository {
    void create (IdTypeEntity entity);
    void update (IdTypeEntity entity);
    void delete (UUID id);
    IdTypeEntity findById(UUID id);
    List<IdTypeEntity> findByFilter(IdTypeEntity entity);
    List<IdTypeEntity> findAll();
}
