package co.edu.uco.ucoparking.features.student.registernewstudent.application.usecase.mapper;

public interface MapperDomain <D,E> {

    E toEntity(D domain);

    D toDomain(E entity);
}
