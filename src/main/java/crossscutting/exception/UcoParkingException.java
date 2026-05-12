package crossscutting.exception;

import crossscutting.messagescatalog.MessagesEnum;

import java.util.Arrays;
import java.util.Objects;

public class UcoParkingException extends RuntimeException {

    private static final long serialVersionUID = -5881677798523712233L;

    private final MessagesEnum messageCode;
    private final Object[] messageArgs;
    private final MessagesEnum technicalMessageCode;
    private final Object[] technicalMessageArgs;

    private UcoParkingException(
            final MessagesEnum messageCode,
            final Object[] messageArgs,
            final MessagesEnum technicalMessageCode,
            final Object[] technicalMessageArgs,
            final Throwable cause) {
        super(cause);
        this.messageCode = Objects.requireNonNull(messageCode, "messageCode");
        this.messageArgs = copyArgs(messageArgs);
        this.technicalMessageCode = Objects.requireNonNullElse(technicalMessageCode, messageCode);
        this.technicalMessageArgs = copyArgs(technicalMessageArgs);
    }

    public static UcoParkingException of(final MessagesEnum messageCode, final Object... messageArgs) {
        return new UcoParkingException(messageCode, messageArgs, messageCode, messageArgs, null);
    }

    public static UcoParkingException of(
            final MessagesEnum messageCode,
            final Throwable cause,
            final Object... messageArgs) {
        return new UcoParkingException(messageCode, messageArgs, messageCode, messageArgs, cause);
    }

    public static UcoParkingException of(
            final MessagesEnum messageCode,
            final MessagesEnum technicalMessageCode,
            final Object... messageArgs) {
        return new UcoParkingException(messageCode, messageArgs, technicalMessageCode, messageArgs, null);
    }

    public static UcoParkingException of(
            final MessagesEnum messageCode,
            final MessagesEnum technicalMessageCode,
            final Throwable cause,
            final Object... messageArgs) {
        return new UcoParkingException(messageCode, messageArgs, technicalMessageCode, messageArgs, cause);
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

    private static Object[] copyArgs(final Object[] args) {
        return args == null ? new Object[0] : Arrays.copyOf(args, args.length);
    }
}
