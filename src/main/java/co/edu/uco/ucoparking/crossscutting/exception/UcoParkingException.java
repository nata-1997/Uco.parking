package co.edu.uco.ucoparking.crossscutting.exception;

import co.edu.uco.ucoparking.crossscutting.helper.ObjectHelper;
import co.edu.uco.ucoparking.crossscutting.helper.TextHelper;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;

import java.util.Arrays;
import java.util.Objects;

public class UcoParkingException extends RuntimeException {

    private static final long serialVersionUID = -5881677798523712233L;

    private final MessagesEnum messageCode;
    private final Object[] messageArgs;
    private final MessagesEnum technicalMessageCode;
    private final Object[] technicalMessageArgs;
    private Throwable rootException;
    private String userMessage;
    private String technicalMessage;

    private UcoParkingException(
            final MessagesEnum messageCode,
            final Object[] messageArgs,
            final MessagesEnum technicalMessageCode,
            final Object[] technicalMessageArgs,
            final Throwable rootException,
            final String userMessage,
            final String technicalMessage) {
        super(rootException);
        this.messageCode = messageCode;
        this.messageArgs = copyArgs(messageArgs);
        this.technicalMessageCode = technicalMessageCode;
        this.technicalMessageArgs = copyArgs(technicalMessageArgs);
        setRootException(rootException);
        setUserMessage(userMessage);
        setTechnicalMessage(technicalMessage);
    }

    public static UcoParkingException of(final MessagesEnum messageCode, final Object... messageArgs) {
        return new UcoParkingException(messageCode, messageArgs, messageCode, messageArgs, null, null, null);
    }

    public static UcoParkingException of(
            final MessagesEnum messageCode,
            final Throwable cause,
            final Object... messageArgs) {
        return new UcoParkingException(messageCode, messageArgs, messageCode, messageArgs, cause, null, null);
    }

    public static UcoParkingException of(
            final MessagesEnum messageCode,
            final MessagesEnum technicalMessageCode,
            final Object... messageArgs) {
        return new UcoParkingException(messageCode, messageArgs, technicalMessageCode, messageArgs, null, null, null);
    }

    public static UcoParkingException create(final String userMessage) {
        return new UcoParkingException(null, null, null, null, new Exception(), userMessage, userMessage);
    }

    public static UcoParkingException create(final String userMessage, final String technicalMessage) {
        return new UcoParkingException(null, null, null, null, new Exception(), userMessage, technicalMessage);
    }

    public static UcoParkingException create(
            final Throwable rootException,
            final String userMessage,
            final String technicalMessage) {
        return new UcoParkingException(null, null, null, null, rootException, userMessage, technicalMessage);
    }

    public MessagesEnum getMessageCode() {
        return messageCode;
    }

    public Object[] getMessageArgs() {
        return copyArgs(messageArgs);
    }

    public MessagesEnum getTechnicalMessageCode() {
        return technicalMessageCode;
    }

    public Object[] getTechnicalMessageArgs() {
        return copyArgs(technicalMessageArgs);
    }

    public boolean hasCatalogMessageCode() {
        return !Objects.isNull(messageCode);
    }

    public Throwable getRootException() {
        return rootException;
    }

    private void setRootException(final Throwable rootException) {
        this.rootException = ObjectHelper.getDefault(rootException, new Exception());
    }

    public String getUserMessage() {
        return userMessage;
    }

    private void setUserMessage(final String userMessage) {
        this.userMessage = TextHelper.isNullOrWhiteSpace(userMessage) ? null : TextHelper.getDefaultWithTrim(userMessage);
    }

    public String getTechnicalMessage() {
        return technicalMessage;
    }

    private void setTechnicalMessage(final String technicalMessage) {
        this.technicalMessage = TextHelper.isNullOrWhiteSpace(technicalMessage)
                ? null
                : TextHelper.getDefaultWithTrim(technicalMessage);
    }

    private static Object[] copyArgs(final Object[] args) {
        return args == null ? new Object[0] : Arrays.copyOf(args, args.length);
    }
}
