package infrastructure.persistence.repository.adapter.sql.jpa;

import infrastructure.persistence.entity.AcademicProgramEntity;
import infrastructure.persistence.repository.AcademicProgramRepository;
import infrastructure.persistence.sql.AcademicProgramJpaRepository;
import infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;



import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AcademicProgramRepositoryJpaAdapter implements AcademicProgramRepository {

    private AcademicProgramJpaRepository repository;

    public AcademicProgramRepositoryJpaAdapter(AcademicProgramJpaRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public void create(AcademicProgramEntity entity) {
        AcademicProgramJPAEntity jpaEntity = null; //Mappper
        repository.save (jpaEntity);
    }

    @Override
    public void update(AcademicProgramEntity entity) {
        AcademicProgramJPAEntity jpaEntity = null; //Mappper
        repository.save (jpaEntity);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById (id);

    }

    @Override
    public AcademicProgramEntity findById(UUID id) {
        Optional<AcademicProgramJPAEntity> jpaEntity = repository.findById(id);
        AcademicProgramEntity entityReturn = null; //Mapper//
        return entityReturn;
    }

    @Override
    public List<AcademicProgramEntity> findByFilter(AcademicProgramEntity entity) {
        return List.of();
    }

    @Override
    public List<AcademicProgramEntity> findAll() {
        return List.of();
    }
}
