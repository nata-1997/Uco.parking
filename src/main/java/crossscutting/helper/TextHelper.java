package crossscutting.helper;


public final class TextHelper {

    private static final String empty = " ";

    private TextHelper() {
    }

    public static String getDefault() {
        return empty;
    }

    public static String getDefault(final String value) {
        return ObjectHelper.getDefault(value, getDefault());
    }

    public static String getDefaultWithTrim(final String value) {
        return getDefault(value).trim();
    }

    public static boolean isEmpty(final String value) {
        return empty.equals(getDefault(value));
    }

    public static boolean isNullOrWhiteSpace(final String value) {
        return ObjectHelper.isNull(value) || value.trim().isEmpty();
    }

    public static boolean lengthIsValid(final String value, final int minLength, final int maxLength, final boolean mustApplyTrim) {
        var valueToValidate = mustApplyTrim ? getDefaultWithTrim(value) : getDefault(value);
        var length = valueToValidate.length();
        return length >= minLength && length <= maxLength;
    }

    public static boolean lengthIsValidWithTrim(final String value, final int minLength, final int maxLength, final boolean mustApplytrim) {
        return lengthIsValid(value, minLength, maxLength, true);
    }

    public static boolean formatIsValid(final String value, final String pattern, final boolean mustApplyTrim) {
        var text = (mustApplyTrim
                ? getDefaultWithTrim(value)
                :getDefault(value));

        return text.matches(pattern);
    }

}

