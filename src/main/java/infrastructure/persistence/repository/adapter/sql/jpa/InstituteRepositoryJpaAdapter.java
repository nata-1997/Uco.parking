package infrastructure.persistence.repository.adapter.sql.jpa;

import infrastructure.persistence.entity.InstituteEntity;
import infrastructure.persistence.repository.InstituteRepository;
import infrastructure.persistence.sql.InstituteJpaRepository;
import infrastructure.persistence.sql.entity.InstituteJPAEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InstituteRepositoryJpaAdapter implements InstituteRepository {

    private InstituteJpaRepository repository;

    public InstituteRepositoryJpaAdapter(InstituteJpaRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public void create(InstituteEntity entity) {
        InstituteJPAEntity jpaEntity = null; //Mappper
        repository.save (jpaEntity);

    }

    @Override
    public void update(InstituteEntity entity) {
        InstituteJPAEntity jpaEntity = null; //Mappper
        repository.save (jpaEntity);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById (id);

    }

    @Override
    public InstituteEntity findById(UUID id) {
        Optional<InstituteJPAEntity> jpaEntity = repository.findById(id);
        InstituteEntity entityReturn = null; //Mapper//
        return entityReturn;
    }


    @Override
    public List<InstituteEntity> findByFilter(InstituteEntity entity) {
        return List.of();
    }

    @Override
    public List<InstituteEntity> findAll() {
        return List.of();
    }
}
