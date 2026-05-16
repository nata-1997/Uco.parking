package co.edu.uco.ucoparking.infrastructure.persistence.mapper;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.IdTypeEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.IdTypeJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IdTypeMapperJPA extends MapperJPA<IdTypeEntity, IdTypeJPAEntity> {

    @Override
    default IdTypeEntity toEntity(final IdTypeJPAEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        return new IdTypeEntity(jpaEntity.getId(), jpaEntity.getName());
    }
}
