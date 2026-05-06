package infrastructure.persistence.repository.adapter.sql.jpa;

import infrastructure.persistence.entity.InstituteEntity;
import infrastructure.persistence.repository.InstitutionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class InstitutionRepositoryJpaAdapter implements InstitutionRepository{
    private InstitutionJPARepository repositoy;

    public InstitutionRepositoryJpaAdapter(InstitutionRepository repository){
        super();
        this.repository = repository;
    }

    @Override
    public void create(InstituteEntity entity) {

    }

    @Override
    public void update(InstituteEntity entity) {

    }

    @Override
    public void delete(InstituteEntity entity) {

    }

    @Override
    public InstituteEntity findById(UUID id) {
        return null;
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
