package infrastructure.persistence.repository.adapter.sql.jpa;

import infrastructure.persistence.entity.InstituteEntity;
import infrastructure.persistence.mapper.InstituteMapperJPA;
import infrastructure.persistence.repository.InstituteRepository;
import infrastructure.persistence.sql.InstituteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class InstituteRepositoryJpaAdapter implements InstituteRepository {

    private final InstituteJpaRepository repository;
    private final InstituteMapperJPA mapper;

    public InstituteRepositoryJpaAdapter(InstituteJpaRepository repository,InstituteMapperJPA mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(InstituteEntity entity) {
        repository.save (mapper.toJPAEntity(entity));
    }

    @Override
    public void update(InstituteEntity entity) {
        repository.save (mapper.toJPAEntity(entity));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById (id);

    }

    @Override
    public InstituteEntity findById(UUID id) {
        return repository.findById(id).map(mapper::toEntity).orElse(null);
    }


    @Override
    public List<InstituteEntity> findByFilter(InstituteEntity entity) {
        return List.of();
    }

    @Override
    public List<InstituteEntity> findAll() {
        return repository.findAll().stream().map(mapper::toEntity).toList();
    }
}
