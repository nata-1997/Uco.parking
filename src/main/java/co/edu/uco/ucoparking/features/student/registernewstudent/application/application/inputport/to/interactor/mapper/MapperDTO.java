package co.edu.uco.ucoparking.features.student.registernewstudent.application.application.inputport.to.interactor.mapper;

public interface MapperDTO <T, D> {

    T toDTO(D domain);

    D toDomain(T dto);
}
