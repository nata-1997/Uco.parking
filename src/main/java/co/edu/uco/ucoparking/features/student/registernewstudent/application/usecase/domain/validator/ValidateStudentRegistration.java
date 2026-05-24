package co.edu.uco.ucoparking.features.student.registernewstudent.application.usecase.domain.validator;

import co.edu.uco.ucoparking.crossscutting.specification.generics.EmailFormatSpecification;
import co.edu.uco.ucoparking.crossscutting.specification.generics.StringLengthSpecification;
import co.edu.uco.ucoparking.crossscutting.specification.generics.StringValuePresentSpecification;
import co.edu.uco.ucoparking.crossscutting.specification.generics.UuidValuePresentSpecification;
import co.edu.uco.ucoparking.crossscutting.validator.Validator;
import co.edu.uco.ucoparking.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.crossscutting.exception.UcoParkingException;
import co.edu.uco.ucoparking.crossscutting.helper.ObjectHelper;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import org.springframework.stereotype.Component;

@Component
public class ValidateStudentRegistration implements Validator {

    private static final int MOBILE_MIN_LENGTH = 7;
    private static final int MOBILE_MAX_LENGTH = 15;

    private final StringValuePresentSpecification stringValuePresentSpecification = new StringValuePresentSpecification();
    private final UuidValuePresentSpecification uuidValuePresentSpecification = new UuidValuePresentSpecification();
    private final EmailFormatSpecification emailFormatSpecification = new EmailFormatSpecification();
    private final StringLengthSpecification mobileLengthSpecification =
            new StringLengthSpecification(MOBILE_MIN_LENGTH, MOBILE_MAX_LENGTH, true);

    @Override
    public void validate(final Object... data) {
        if (ObjectHelper.isNull(data) || data.length < 1 || !(data[0] instanceof RegisterNewStudentDomain candidate)) {
            throw UcoParkingException.of(MessagesEnum.COMMON_VALIDATION_ERROR);
        }

        validate(candidate);
    }

    public void validate(final RegisterNewStudentDomain candidate) {
        if (!stringValuePresentSpecification.isSatisfiedBy(candidate.getName())
                || !stringValuePresentSpecification.isSatisfiedBy(candidate.getLastName())) {
            throw UcoParkingException.of(MessagesEnum.STUDENT_MANDATORY_FIELD_MISSING);
        }

        if (!uuidValuePresentSpecification.isSatisfiedBy(candidate.getAcademicProgram())) {
            throw UcoParkingException.of(MessagesEnum.STUDENT_ACADEMIC_PROGRAM_REQUIRED);
        }

        if (!uuidValuePresentSpecification.isSatisfiedBy(candidate.getIdType())) {
            throw UcoParkingException.of(MessagesEnum.STUDENT_ID_TYPE_REQUIRED);
        }

        if (!stringValuePresentSpecification.isSatisfiedBy(candidate.getIdNumber())) {
            throw UcoParkingException.of(MessagesEnum.STUDENT_ID_NUMBER_INVALID);
        }

        if (!emailFormatSpecification.isSatisfiedBy(candidate.getEmail())) {
            throw UcoParkingException.of(MessagesEnum.STUDENT_EMAIL_INVALID);
        }

        if (!mobileLengthSpecification.isSatisfiedBy(candidate.getMobileNumber())) {
            throw UcoParkingException.of(MessagesEnum.STUDENT_MOBILE_NUMBER_INVALID);
        }
    }
}
