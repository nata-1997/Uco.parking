package infrastructure.persistence.mapper;

import infrastructure.persistence.entity.AcademicProgramEntity;
import infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {InstituteMapperJPA.class})
public interface AcademicProgramMapperJPA extends MapperJPA<AcademicProgramEntity, AcademicProgramJPAEntity>{
}
