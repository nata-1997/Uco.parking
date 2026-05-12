package infrastructure.persistence.mapper;

import infrastructure.persistence.entity.StudentEntity;
import infrastructure.persistence.sql.entity.StudentJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AcademicProgramMapperJPA.class, IdTypeMapperJPA.class})
public interface StudentMapperJPA extends MapperJPA<StudentEntity, StudentJPAEntity>{
    //Source y target opcionales//
    @Mapping(source = "eMail", target = "eMail")
    StudentJPAEntity ToJPAEntity(StudentEntity entity);

    @Mapping(source = "eMail", target = "eMail")
    StudentEntity toEntity(StudentJPAEntity jpaEntity);


}
