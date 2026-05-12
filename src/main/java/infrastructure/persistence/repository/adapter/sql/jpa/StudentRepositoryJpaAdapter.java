package infrastructure.persistence.repository.adapter.sql.jpa;

import infrastructure.persistence.entity.StudentEntity;
import infrastructure.persistence.mapper.StudentMapperJPA;
import infrastructure.persistence.repository.StudentRepository;
import infrastructure.persistence.sql.StudentJpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public class StudentRepositoryJpaAdapter implements StudentRepository {

    private final StudentJpaRepository repository;

    private final StudentMapperJPA mapper;

    public StudentRepositoryJpaAdapter(StudentJpaRepository repository, StudentMapperJPA mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(StudentEntity entity) {
        repository.save (mapper.ToJPAEntity(entity));

    }

    @Override
    public void update(StudentEntity entity) {
        repository.save (mapper.ToJPAEntity(entity));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById (id);

    }

    @Override
    public StudentEntity findById(UUID id) {
        return repository.findById(id).map(mapper::toEntity).orElse(null);
    }

    @Override
    public List<StudentEntity> findByFilter(StudentEntity entity) {
        return List.of();
    }

    @Override
    public List<StudentEntity> findAll() {
        return repository.findAll().stream().map(mapper::toEntity).toList();
    }
}
