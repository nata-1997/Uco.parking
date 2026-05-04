package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.specification;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;

public final class LastNameSpecification  implements StudentRegistrationSpecification{
    private static final int MIN_LEN = 1;
    private static final int MAX_LEN = 100;

    @Override
    public boolean isSatisfiedBy(RegisterNewStudentDomain candidate) {
        String lastname = candidate.getLastName();
        if (lastname == null || lastname.isEmpty()) {
            return false;
        }
        int len = lastname.length();
        return len >= MIN_LEN && len <= MAX_LEN;
    }

    @Override
    public String dissatisfactionReason(RegisterNewStudentDomain candidate) {
        String lastname = candidate.getLastName();
        if (lastname == null || lastname.isEmpty()) {
            return "El apellido es obligatorio";
        }
        int len = lastname.length();
        if (len < MIN_LEN || len > MAX_LEN) {
            return "El apellido debe tener entre " + MIN_LEN + " y " + MAX_LEN + " caracteres";
         }
        return null;
    }
}
