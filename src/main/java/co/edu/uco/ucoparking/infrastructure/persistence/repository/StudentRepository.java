package co.edu.uco.ucoparking.infrastructure.persistence.repository;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;

import java.util.List;
import java.util.UUID;


public interface StudentRepository {

    void create (StudentEntity entity);
    void update (StudentEntity entity);
    void delete (UUID id);
    StudentEntity findById(UUID id);
    List<StudentEntity> findByFilter(StudentEntity entity);
    List<StudentEntity> findAll();

}
