package infrastructure.persistence.crossscutting.Helper;

public class EmailHelper {

    private static final  String DEFAULT_EMAIL = "default@example.com";

    private EmailHelper() {
    }

    public static String getDefault(){
        return DEFAULT_EMAIL;
    }

    public static String getdefaultWithvalidation(final String email){
        String trimmedEmail = TextHelper.getDefaultWithTrim(email);
        return (TextHelper.isNullOrWhiteSpace(trimmedEmail) || !isValidEmail(trimmedEmail)) ? getDefault() : trimmedEmail;
    }

    private static boolean isValidEmail(final String email){
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }
}
