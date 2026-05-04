package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.specification;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;

final class AndStudentRegistrationSpecification implements StudentRegistrationSpecification {

    private final StudentRegistrationSpecification left;
    private final StudentRegistrationSpecification right;

    AndStudentRegistrationSpecification(
            StudentRegistrationSpecification left,
            StudentRegistrationSpecification right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(RegisterNewStudentDomain candidate) {
        return left.isSatisfiedBy(candidate) && right.isSatisfiedBy(candidate);
    }

    @Override
    public String dissatisfactionReason(RegisterNewStudentDomain candidate) {
        if (!left.isSatisfiedBy(candidate)) {
            return left.dissatisfactionReason(candidate);
        }
        if (!right.isSatisfiedBy(candidate)) {
            return right.dissatisfactionReason(candidate);
        }
        return null;
    }
}
