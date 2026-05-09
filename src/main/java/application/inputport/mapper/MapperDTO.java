package application.inputport.mapper;

@Mapper
public interface MapperDTO <T, D> {

    T toDTO(D domain);

    D toDomain(T dto);
}
