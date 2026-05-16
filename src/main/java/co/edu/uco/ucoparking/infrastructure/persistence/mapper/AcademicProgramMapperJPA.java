package co.edu.uco.ucoparking.infrastructure.persistence.mapper;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.AcademicProgramEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.InstituteJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;


@Mapper(componentModel = "spring")
public interface AcademicProgramMapperJPA extends MapperJPA<AcademicProgramEntity, AcademicProgramJPAEntity> {

    @Override
    @Mapping(source = "instituteEntity", target = "instituteEntity", qualifiedByName = "instituteRef")
    AcademicProgramJPAEntity toJPAEntity(AcademicProgramEntity entity);

    @Override
    @Mapping(source = "instituteEntity.id", target = "instituteEntity")
    AcademicProgramEntity toEntity(AcademicProgramJPAEntity jpaEntity);

    @Named("instituteRef")
    default InstituteJPAEntity instituteRef(final UUID id) {
        if (id == null) {
            return null;
        }
        return new InstituteJPAEntity(id, null);
    }
}
