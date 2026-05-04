package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.specification;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;

public final class StudentRegistrationSpecifications {

    private static final StudentRegistrationSpecification REGISTER_NEW_STUDENT =
            new AcademicProgramAssignedSpecification()
                    .and(new IdTypeAssignedSpecification())
                    .and(new FirstNameSpecification())
                    .and(new LastNameSpecification())
                    .and(new DocumentNumberSpecification())
                    .and(new EmailFormatSpecification())
                    .and(new MobileNumberSpecification());

    private StudentRegistrationSpecifications() {

    }

    public static void assertAll(RegisterNewStudentDomain candidate) {
        REGISTER_NEW_STUDENT.assertSatisfiedBy(candidate);
    }

}
