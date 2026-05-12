package infrastructure.persistence.repository.adapter.sql.jpa;

import infrastructure.persistence.entity.IdTypeEntity;
import infrastructure.persistence.mapper.IdTypeMapperJPA;
import infrastructure.persistence.repository.IdTypeRepository;
import infrastructure.persistence.sql.IdTypeJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class IdTypeRepositoryJpaAdapter implements IdTypeRepository {


    private final IdTypeJpaRepository repository;
    private final IdTypeMapperJPA mapper;

    public IdTypeRepositoryJpaAdapter(IdTypeJpaRepository repository, IdTypeMapperJPA mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(IdTypeEntity entity) {
        repository.save (mapper.toJPAEntity(entity));

    }

    @Override
    public void update(IdTypeEntity entity) {
        repository.save (mapper.toJPAEntity(entity));

    }

    @Override
    public void delete(UUID id) {
        repository.deleteById (id);

    }

    @Override
    public IdTypeEntity findById(UUID id) {
        return repository.findById (id).map(mapper::toEntity).orElse(null);

    }

    @Override
    public List<IdTypeEntity> findByFilter(IdTypeEntity entity) {
        return List.of();
    }

    @Override
    public List<IdTypeEntity> findAll() {
        return repository.findAll().stream().map(mapper::toEntity).toList();
    }
}
