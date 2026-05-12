package crossscutting.helper;

import crossscutting.constants.DefaultValues;

public final class EmailHelper {

    private EmailHelper() {
    }

    public static String getDefault(){
        return DefaultValues.EMAIL_SENTINEL;
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
