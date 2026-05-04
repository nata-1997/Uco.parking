package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.specification;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.exception.InvalidStudentRegistrationException;

public interface StudentRegistrationSpecification {

    boolean isSatisfiedBy(RegisterNewStudentDomain candidate);

    String dissatisfactionReason(RegisterNewStudentDomain candidate);

    default StudentRegistrationSpecification and(StudentRegistrationSpecification other) {
        return new AndStudentRegistrationSpecification(this, other);
    }

    default void assertSatisfiedBy(RegisterNewStudentDomain candidate) {
        if (!isSatisfiedBy(candidate)) {
            String message = dissatisfactionReason(candidate);
            if (message == null || message.isBlank()) {
                message = "Los datos de registro del estudiante no son válidos.";
            }
            throw new InvalidStudentRegistrationException(message);
        }
    }

}
