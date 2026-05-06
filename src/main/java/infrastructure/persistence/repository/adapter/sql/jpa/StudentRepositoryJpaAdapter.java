package infrastructure.persistence.repository.adapter.sql.jpa;

import infrastructure.persistence.entity.StudentEntity;
import infrastructure.persistence.repository.StudentRepository;
import infrastructure.persistence.sql.StudentJpaRepository;
import infrastructure.persistence.sql.entity.StudentJPAEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StudentRepositoryJpaAdapter implements StudentRepository {

    private StudentJpaRepository repository;

    public StudentRepositoryJpaAdapter(StudentJpaRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public void create(StudentEntity entity) {
        StudentJPAEntity jpaEntity = null; //Mappper
        repository.save (jpaEntity);

    }

    @Override
    public void update(StudentEntity entity) {
        StudentJPAEntity jpaEntity = null; //Mappper
        repository.save (jpaEntity);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById (id);

    }

    @Override
    public StudentEntity findById(UUID id) {
        Optional<StudentJPAEntity> jpaEntity = repository.findById(id);
        StudentEntity entityReturn = null; //Mapper//
        return entityReturn;
    }

    @Override
    public List<StudentEntity> findByFilter(StudentEntity entity) {
        return List.of();
    }

    @Override
    public List<StudentEntity> findAll() {
        return List.of();
    }
}
