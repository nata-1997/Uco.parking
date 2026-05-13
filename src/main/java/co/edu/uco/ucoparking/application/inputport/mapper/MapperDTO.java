package co.edu.uco.ucoparking.application.inputport.mapper;

public interface MapperDTO <T, D> {

    T toDTO(D domain);

    D toDomain(T dto);
}
