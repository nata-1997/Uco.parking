package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.specification;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;

public final class FirstNameSpecification implements StudentRegistrationSpecification {

    private static final int MIN_LEN = 1;
    private static final int MAX_LEN = 100;

    @Override
    public boolean isSatisfiedBy(RegisterNewStudentDomain candidate) {
        String name = candidate.getName();
        if (name == null || name.isBlank()) {
            return false;
        }
        int len = name.length();
        return len >= MIN_LEN && len <= MAX_LEN;
    }

    @Override
    public String dissatisfactionReason(RegisterNewStudentDomain candidate) {
        String name = candidate.getName();
        if (name == null || name.isBlank()) {
            return "El nombre es obligatorio.";
        }
        int len = name.length();
        if (len < MIN_LEN || len > MAX_LEN) {
            return "El nombre debe tener entre " + MIN_LEN + " y " + MAX_LEN + " caracteres.";
        }
        return null;
    }
}
