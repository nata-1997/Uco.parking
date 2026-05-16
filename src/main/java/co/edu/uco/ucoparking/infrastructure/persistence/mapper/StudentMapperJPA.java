package co.edu.uco.ucoparking.infrastructure.persistence.mapper;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.StudentJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapperJPA extends MapperJPA<StudentEntity, StudentJPAEntity> {
}
