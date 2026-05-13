package co.edu.uco.ucoparking.infrastructure.persistence.mapper;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.AcademicProgramEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.InstituteJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AcademicProgramMapperJPA {

    @Mapping(target = "instituteEntity", expression = "java(toInstituteRef(entity.getInstituteEntity()))")
    AcademicProgramJPAEntity toJPAEntity(AcademicProgramEntity entity);

    @Mapping(source = "instituteEntity.id", target = "instituteEntity")
    AcademicProgramEntity toEntity(AcademicProgramJPAEntity jpaEntity);

    default InstituteJPAEntity toInstituteRef(UUID id) {
        if (id == null) {
            return null;
        }
        return new InstituteJPAEntity(id, null);
    }
}
