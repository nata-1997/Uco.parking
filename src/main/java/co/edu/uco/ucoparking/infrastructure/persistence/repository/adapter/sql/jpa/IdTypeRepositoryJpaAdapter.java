package co.edu.uco.ucoparking.infrastructure.persistence.repository.adapter.sql.jpa;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.IdTypeEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.mapper.IdTypeMapperJPA;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.IdTypeRepository;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.IdTypeJpaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class IdTypeRepositoryJpaAdapter implements IdTypeRepository {

    private final IdTypeJpaRepository repository;
    private final IdTypeMapperJPA mapper;

    public IdTypeRepositoryJpaAdapter(final IdTypeJpaRepository repository, final IdTypeMapperJPA mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @CacheEvict(value = "idTypes", allEntries = true)
    public void create(final IdTypeEntity entity) {
        repository.save(mapper.toJPAEntity(entity));
    }

    @Override
    @CacheEvict(value = "idTypes", allEntries = true)
    public void update(final IdTypeEntity entity) {
        repository.save(mapper.toJPAEntity(entity));
    }

    @Override
    @CacheEvict(value = "idTypes", allEntries = true)
    public void delete(final UUID id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = "idTypes", key = "#id")
    public IdTypeEntity findById(final UUID id) {
        return repository.findById(id).map(mapper::toEntity).orElse(null);
    }

    @Override
    public List<IdTypeEntity> findByFilter(final IdTypeEntity entity) {
        return List.of();
    }

    @Override
    @Cacheable(value = "idTypes")
    public List<IdTypeEntity> findAll() {
        return repository.findAll().stream().map(mapper::toEntity).toList();
    }
}