package co.edu.uco.ucoparking.features.student.registernewstudent.application.application.usecase.mapper;

public interface MapperDTO <T, D> {

    T toDTO(D domain);

    D toDomain(T dto);
}
