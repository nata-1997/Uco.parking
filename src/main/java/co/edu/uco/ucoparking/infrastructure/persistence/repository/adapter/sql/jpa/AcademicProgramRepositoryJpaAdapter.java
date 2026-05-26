package co.edu.uco.ucoparking.infrastructure.persistence.repository.adapter.sql.jpa;

import co.edu.uco.ucoparking.infrastructure.cache.CacheNames;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.AcademicProgramEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.mapper.AcademicProgramMapperJPA;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.AcademicProgramRepository;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.AcademicProgramJpaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AcademicProgramRepositoryJpaAdapter implements AcademicProgramRepository {

    private final AcademicProgramJpaRepository repository;
    private final AcademicProgramMapperJPA mapper;

    public AcademicProgramRepositoryJpaAdapter(final AcademicProgramJpaRepository repository, final AcademicProgramMapperJPA mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @CacheEvict(value = CacheNames.ACADEMIC_PROGRAMS, allEntries = true)
    public void create(final AcademicProgramEntity entity) {
        repository.save(mapper.toJPAEntity(entity));
    }

    @Override
    @CacheEvict(value = CacheNames.ACADEMIC_PROGRAMS, allEntries = true)
    public void update(final AcademicProgramEntity entity) {
        repository.save(mapper.toJPAEntity(entity));
    }

    @Override
    @CacheEvict(value = CacheNames.ACADEMIC_PROGRAMS, allEntries = true)
    public void delete(final UUID id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = CacheNames.ACADEMIC_PROGRAMS, key = "#id", unless = "#result == null")
    public AcademicProgramEntity findById(final UUID id) {
        return repository.findById(id).map(mapper::toEntity).orElse(null);
    }

    @Override
    public List<AcademicProgramEntity> findByFilter(final AcademicProgramEntity entity) {
        return List.of();
    }

    @Override
    @Cacheable(value = CacheNames.ACADEMIC_PROGRAMS)
    public List<AcademicProgramEntity> findAll() {
        return repository.findAll().stream().map(mapper::toEntity).toList();
    }
}