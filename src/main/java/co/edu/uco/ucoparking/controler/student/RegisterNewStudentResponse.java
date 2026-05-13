package co.edu.uco.ucoparking.controler.student;

public class RegisterNewStudentResponse {

    private final String code;
    private final String message;

    public RegisterNewStudentResponse(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
