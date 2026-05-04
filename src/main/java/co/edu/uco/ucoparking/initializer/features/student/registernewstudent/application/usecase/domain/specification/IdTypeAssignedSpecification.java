package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.specification;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;

public final class IdTypeAssignedSpecification implements StudentRegistrationSpecification {
    @Override
    public boolean isSatisfiedBy(RegisterNewStudentDomain candidate) {
        return candidate.getIdType() != null;
    }

    @Override
    public String dissatisfactionReason(RegisterNewStudentDomain candidate) {
        if (isSatisfiedBy(candidate)) {
            return null;
        }
        return "El tipo de documento es obligatorio.";
    }
}
