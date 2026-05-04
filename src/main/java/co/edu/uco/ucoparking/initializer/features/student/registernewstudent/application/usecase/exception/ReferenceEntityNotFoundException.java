package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.exception;

public class ReferenceEntityNotFoundException extends RuntimeException {

    public ReferenceEntityNotFoundException(String message) {
        super(message);
    }
}
