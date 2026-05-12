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
        return (TextHelper.isNullOrWhiteSpace(trimmedEmail) || !isValidFormat(trimmedEmail)) ? getDefault() : trimmedEmail;
    }

    public static boolean isValidFormat(final String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private static boolean isValidEmail(final String email){
        return isValidFormat(email);
    }
}
