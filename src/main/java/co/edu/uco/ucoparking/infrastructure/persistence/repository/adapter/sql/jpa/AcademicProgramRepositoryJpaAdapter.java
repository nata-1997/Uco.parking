package co.edu.uco.ucoparking.infrastructure.persistence.repository.adapter.sql.jpa;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.AcademicProgramEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.mapper.AcademicProgramMapperJPA;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.AcademicProgramRepository;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.AcademicProgramJpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public class AcademicProgramRepositoryJpaAdapter implements AcademicProgramRepository {

    private final AcademicProgramJpaRepository repository;
    private final AcademicProgramMapperJPA mapper;

    public AcademicProgramRepositoryJpaAdapter(AcademicProgramJpaRepository repository, AcademicProgramMapperJPA mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(AcademicProgramEntity entity) {
        repository.save (mapper.toJPAEntity(entity));
    }

    @Override
    public void update(AcademicProgramEntity entity) {
        repository.save (mapper.toJPAEntity(entity));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById (id);

    }

    @Override
    public AcademicProgramEntity findById(UUID id) {
        return repository.findById(id).map(mapper::toEntity).orElse(null);
    }

    @Override
    public List<AcademicProgramEntity> findByFilter(AcademicProgramEntity entity) {
        return List.of();
    }

    @Override
    public List<AcademicProgramEntity> findAll() {
        return repository.findAll().stream().map(mapper::toEntity).toList();
    }
}
