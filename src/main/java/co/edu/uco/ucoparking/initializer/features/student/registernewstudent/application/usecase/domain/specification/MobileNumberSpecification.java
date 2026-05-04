package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.specification;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;

public class MobileNumberSpecification implements StudentRegistrationSpecification{
    private static final int MIN_PHONE_DIGITS = 7;
    private static final int MAX_PHONE_DIGITS = 15;

    @Override
    public boolean isSatisfiedBy(RegisterNewStudentDomain candidate) {
        String mobile = candidate.getMobileNumber();
        if (mobile == null || mobile.isBlank()) {
            return false;
        }
        long digits = mobile.chars().filter(Character::isDigit).count();
        return digits >= MIN_PHONE_DIGITS && digits <= MAX_PHONE_DIGITS;
    }

    @Override
    public String dissatisfactionReason(RegisterNewStudentDomain candidate) {
        String mobile = candidate.getMobileNumber();
        if (mobile == null || mobile.isBlank()) {
            return "El número móvil es obligatorio.";
        }
        long digits = mobile.chars().filter(Character::isDigit).count();
        if (digits < MIN_PHONE_DIGITS || digits > MAX_PHONE_DIGITS) {
            return "El móvil debe contener entre " + MIN_PHONE_DIGITS + " y " + MAX_PHONE_DIGITS + " dígitos.";
        }
        return null;
    }
}
