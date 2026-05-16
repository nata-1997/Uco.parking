package co.edu.uco.ucoparking.features.student.registernewstudent.application.application.usecase.mapper;

public interface MapperDomain <D,E> {

    D toEntity(D domain);

    E toDomain(D entity);
}
