package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.exception;

public class InvalidStudentRegistrationException extends RuntimeException{

    public InvalidStudentRegistrationException(String message) {
        super(message);
    }
}
