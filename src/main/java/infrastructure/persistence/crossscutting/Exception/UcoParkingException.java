package infrastructure.persistence.crossscutting.Exception;

import infrastructure.persistence.crossscutting.Helper.ObjectHelper;
import infrastructure.persistence.crossscutting.Helper.TextHelper;

public class UcoParkingException extends RuntimeException {

    private static final long serialVersionUID = -5881677798523712233L;

    private Throwable rootException;
    private String UserMessage;
    private String TechnicalMessage;

    public UcoParkingException(final Throwable rootException, final String userMessage, final String technicalMessage) {
        setRootException(rootException);
        setUserMessage(userMessage);
        setTechnicalMessage(technicalMessage);
    }

    public static UcoParkingException create(final String userMessage) {
        return new UcoParkingException(new Exception(), userMessage, technicalMessage);
    }

    public static UcoParkingException create(final String userMessage, final String technicalMessage) {
        return new UcoParkingException(new Exception(), userMessage, technicalMessage);
    }

    public static UcoParkingException create(final Throwable rootException, final String userMessage, final String technicalMessage) {
        return new UcoParkingException(new Exception(), userMessage, technicalMessage);
    }

    public Throwable getRootException() {
        return rootException;
    }

    public void setRootException(final Throwable rootException) {
        this.rootException = ObjectHelper.getDefault(rootException, new Exception());
    }

    public String getUserMessage() {
        return UserMessage;
    }

    public void setUserMessage(final String userMessage) {
        this.UserMessage = TextHelper.getDefaultWithTrim(userMessage);
    }

    public String setTechnicalMessage(final String technicalMessage) {
        this.technicalMessage = TextHelper.getDefaultWithTrim(technicalMessage);
    }

    public String

    long getSerialVersionuid() {
        return serialVersionUID;
    }
}

