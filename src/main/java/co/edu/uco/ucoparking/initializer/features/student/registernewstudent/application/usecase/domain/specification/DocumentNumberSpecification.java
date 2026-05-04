package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.specification;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;

public class DocumentNumberSpecification implements StudentRegistrationSpecification{

    private static final int MIN_LEN = 5;
    private static final int MAX_LEN = 30;

    @Override
    public boolean isSatisfiedBy(RegisterNewStudentDomain candidate) {
        String idNumber = candidate.getIdNumber();
        if (idNumber == null || idNumber.isEmpty()) {
            return false;
        }

        int len = idNumber.length();
        if (len < MIN_LEN || len > MAX_LEN) {
            return false;
        }
        return idNumber.chars().allMatch(DocumentNumberSpecification::allowedChar);
    }

    @Override
    public String dissatisfactionReason(RegisterNewStudentDomain candidate) {
        String idNumber = candidate.getIdNumber();
        if (idNumber == null || idNumber.isEmpty()) {
            return "El número del documento es obligatorio.";
        }
        int len = idNumber.length();
        if (len < MIN_LEN || len > MAX_LEN) {
            return "El número de documento debe tener entre " + MIN_LEN + " y " + MAX_LEN + "caracteres.";
        }
        if (!idNumber.chars().allMatch(DocumentNumberSpecification::allowedChar)){
            return  "El número del documento solo admite digitos, letras y guión";
        }
        return null;
    }

    private static boolean allowedChar(int c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '_';
    }
}
