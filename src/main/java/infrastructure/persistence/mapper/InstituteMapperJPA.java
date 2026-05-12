package infrastructure.persistence.mapper;

import ch.qos.logback.core.model.ComponentModel;
import infrastructure.persistence.entity.InstituteEntity;
import infrastructure.persistence.sql.entity.InstituteJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstituteMapperJPA extends MapperJPA<InstituteEntity, InstituteJPAEntity>{

}
