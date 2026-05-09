package application.inputport.mapper;

import org.mapstruct.Mapper;


public interface MapperDTO <T, D> {

    T toDTO(D domain);

    D toDomain(T dto);
}
