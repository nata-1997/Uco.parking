package co.edu.uco.ucoparking.crossscutting.exception;

public final class ApiErrorResponse {

    private final String code;
    private final String message;
    private final String detail;

    public ApiErrorResponse(final String code, final String message, final String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }
}
