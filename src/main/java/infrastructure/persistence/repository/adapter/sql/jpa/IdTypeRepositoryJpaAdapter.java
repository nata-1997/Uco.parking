package infrastructure.persistence.repository.adapter.sql.jpa;

import infrastructure.persistence.entity.AcademicProgramEntity;
import infrastructure.persistence.entity.IdTypeEntity;
import infrastructure.persistence.repository.IdTypeRepository;
import infrastructure.persistence.sql.IdTypeJpaRepository;
import infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;
import infrastructure.persistence.sql.entity.IdTypeJPAEntity;
import infrastructure.persistence.sql.entity.InstituteJPAEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class IdTypeRepositoryJpaAdapter implements IdTypeRepository {


    private IdTypeJpaRepository repository;

    public IdTypeRepositoryJpaAdapter(IdTypeJpaRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public void create(IdTypeEntity entity) {
        IdTypeJPAEntity jpaEntity = null; //Mappper
        repository.save (jpaEntity);

    }

    @Override
    public void update(IdTypeEntity entity) {
        IdTypeJPAEntity jpaEntity = null; //Mappper
        repository.save (jpaEntity);

    }

    @Override
    public void delete(UUID id) {
        repository.deleteById (id);

    }

    @Override
    public IdTypeEntity findById(UUID id) {
        Optional<IdTypeJPAEntity> jpaEntity = repository.findById(id);
        IdTypeEntity entityReturn = null; //Mapper//
        return entityReturn;

    }

    @Override
    public List<IdTypeEntity> findByFilter(IdTypeEntity entity) {
        return List.of();
    }

    @Override
    public List<IdTypeEntity> findAll() {
        return List.of();
    }
}
