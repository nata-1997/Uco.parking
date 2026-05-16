package co.edu.uco.ucoparking.infrastructure.persistence.mapper;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.InstituteEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.InstituteJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstituteMapperJPA extends MapperJPA<InstituteEntity, InstituteJPAEntity> {

    @Override
    default InstituteEntity toEntity(final InstituteJPAEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        return new InstituteEntity(jpaEntity.getId(), jpaEntity.getName());
    }
}
