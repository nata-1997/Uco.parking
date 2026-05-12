package infrastructure.persistence.mapper;

import infrastructure.persistence.entity.IdTypeEntity;
import infrastructure.persistence.sql.entity.IdTypeJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IdTypeMapperJPA extends  MapperJPA<IdTypeEntity, IdTypeJPAEntity> {
}
