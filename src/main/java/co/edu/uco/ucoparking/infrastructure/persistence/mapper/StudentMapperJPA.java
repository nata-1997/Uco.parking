package co.edu.uco.ucoparking.infrastructure.persistence.mapper;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.IdTypeJPAEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.StudentJPAEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface StudentMapperJPA extends MapperJPA<StudentEntity, StudentJPAEntity> {

    @Override
    @Mapping(source = "academicProgramEntity", target = "academicProgramEntity", qualifiedByName = "academicProgramRef")
    @Mapping(source = "idTypeEntity", target = "idTypeEntity", qualifiedByName = "idTypeRef")
    StudentJPAEntity toJPAEntity(StudentEntity entity);

    @AfterMapping
    default void assignStudentId(@MappingTarget final StudentJPAEntity target, final StudentEntity source) {
        target.markAsNew();
        if (source != null && source.getId() != null) {
            target.setId(source.getId());
        } else if (target.getId() == null) {
            target.generateId();
        }
    }

    @Override
    @Mapping(source = "academicProgramEntity.id", target = "academicProgramEntity")
    @Mapping(source = "idTypeEntity.id", target = "idTypeEntity")
    StudentEntity toEntity(StudentJPAEntity jpaEntity);

    @Named("academicProgramRef")
    default AcademicProgramJPAEntity academicProgramRef(final UUID id) {
        if (id == null) {
            return null;
        }
        return new AcademicProgramJPAEntity(id, null, null);
    }

    @Named("idTypeRef")
    default IdTypeJPAEntity idTypeRef(final UUID id) {
        if (id == null) {
            return null;
        }
        return new IdTypeJPAEntity(id, null);
    }
}
