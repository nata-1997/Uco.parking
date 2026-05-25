package co.edu.uco.ucoparking.infrastructure.persistence.repository.adapter.sql.jpa;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.mapper.StudentMapperJPA;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.StudentRepository;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.StudentJpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
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
        repository.save(mapper.toJPAEntity(entity));

    }

    @Override
    public void update(StudentEntity entity) {
        repository.save(mapper.toJPAEntity(entity));
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
    public Optional<StudentEntity> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return repository.findOneByEmailNormalized(email).map(mapper::toEntity);
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
