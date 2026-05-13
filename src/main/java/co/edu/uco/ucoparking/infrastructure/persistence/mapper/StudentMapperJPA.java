package co.edu.uco.ucoparking.infrastructure.persistence.mapper;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.IdTypeJPAEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.StudentJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface StudentMapperJPA {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "academicProgramEntity", expression = "java(toAcademicProgramRef(entity.getAcademicProgramEntity()))")
    @Mapping(target = "idTypeEntity", expression = "java(toIdTypeRef(entity.getIdTypeEntity()))")
    StudentJPAEntity toJPAEntity(StudentEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "academicProgramEntity.id", target = "academicProgramEntity")
    @Mapping(source = "idTypeEntity.id", target = "idTypeEntity")
    StudentEntity toEntity(StudentJPAEntity jpaEntity);

    default AcademicProgramJPAEntity toAcademicProgramRef(UUID id) {
        if (id == null) {
            return null;
        }
        return new AcademicProgramJPAEntity(id, null, null);
    }

    default IdTypeJPAEntity toIdTypeRef(UUID id) {
        if (id == null) {
            return null;
        }
        return new IdTypeJPAEntity(id, null);
    }
}
