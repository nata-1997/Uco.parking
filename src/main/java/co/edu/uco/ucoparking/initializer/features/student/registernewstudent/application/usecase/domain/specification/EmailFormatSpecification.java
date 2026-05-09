package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.specification;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;

import java.util.regex.Pattern;

public final class EmailFormatSpecification implements StudentRegistrationSpecification{
    private static final Pattern EMAIL = Pattern.compile(
            "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$",
            Pattern.CASE_INSENSITIVE);

    //helper// crear los Helpers//
    //buscar catalogos de mensajes, parampetros, baúl de secretos, apiGetway//
    @Override
    public boolean isSatisfiedBy(RegisterNewStudentDomain candidate) {
        String email = candidate.getEmail();
        if (email == null || email.isBlank()) {
            return false;
        }
        return EMAIL.matcher(email).matches();
    }

    @Override
    public String dissatisfactionReason(RegisterNewStudentDomain candidate) {
        String email = candidate.getEmail();
        if (email == null || email.isBlank()) {
            return "El correo electrónico es obligatorio.";
        }
        if (!EMAIL.matcher(email).matches()) {
            return "El formato del correo electrónico no es válido.";
        }
        return null;
    }
}
