package crossscutting.helper;

public final class EmailHelper {

    private static final  String DEFAULT_EMAIL = "default@example.com";

    private EmailHelper() {
    }

    public static String getDefault(){
        return DEFAULT_EMAIL;
    }

    public static String getdefaultWithvalidation(final String email){
        String trimmedEmail = TextHelper.getDefaultWithTrim(email);
        return (TextHelper.isNullOrWhiteSpace(trimmedEmail) || !isValidFormat(trimmedEmail)) ? getDefault() : trimmedEmail;
    }

    public static boolean isValidFormat(final String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private static boolean isValidEmail(final String email){
        return isValidFormat(email);
    }
}
