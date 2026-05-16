package co.edu.uco.ucoparking.infrastructure.persistence.mapper;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.AcademicProgramEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AcademicProgramMapperJPA extends MapperJPA<AcademicProgramEntity, AcademicProgramJPAEntity>{

    @Mapping(target = "instituteEntity", expression = "java(toInstituteRef(entity.getInstituteEntity()))")
    AcademicProgramJPAEntity toJPAEntity(AcademicProgramEntity entity);

    @Mapping(source = "instituteEntity.id", target = "instituteEntity")
    AcademicProgramEntity toEntity(AcademicProgramJPAEntity jpaEntity);
}
