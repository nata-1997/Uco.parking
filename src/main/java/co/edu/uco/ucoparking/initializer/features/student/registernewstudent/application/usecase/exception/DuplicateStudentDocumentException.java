package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.exception;

public class DuplicateStudentDocumentException extends RuntimeException {

    public DuplicateStudentDocumentException(String message) {
        super(message);
    }
}
