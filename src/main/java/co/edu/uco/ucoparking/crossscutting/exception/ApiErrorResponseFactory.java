package co.edu.uco.ucoparking.crossscutting.exception;

import co.edu.uco.ucoparking.crossscutting.helper.TextHelper;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessageCatalog;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ApiErrorResponseFactory {

    private final MessageCatalog messageCatalog;

    public ApiErrorResponseFactory(final MessageCatalog messageCatalog) {
        this.messageCatalog = messageCatalog;
    }

    public ApiErrorResponse fromUcoParkingException(
            final UcoParkingException exception,
            final Locale locale) {
        if (exception.hasCatalogMessageCode()) {
            return new ApiErrorResponse(
                    exception.getMessageCode().getCode(),
                    messageCatalog.getUserMessage(exception.getMessageCode(), locale, exception.getMessageArgs()),
                    messageCatalog.getTechnicalMessage(
                            exception.getTechnicalMessageCode(),
                            locale,
                            exception.getTechnicalMessageArgs()));
        }

        return new ApiErrorResponse(
                MessagesEnum.COMMON_UNEXPECTED_ERROR.getCode(),
                resolveLegacyMessage(exception.getUserMessage(), locale),
                resolveLegacyMessage(exception.getTechnicalMessage(), locale));
    }

    public ApiErrorResponse fromUnexpectedException(final Locale locale) {
        return new ApiErrorResponse(
                MessagesEnum.COMMON_UNEXPECTED_ERROR.getCode(),
                messageCatalog.getUserMessage(MessagesEnum.COMMON_UNEXPECTED_ERROR, locale),
                messageCatalog.getTechnicalMessage(MessagesEnum.COMMON_UNEXPECTED_ERROR, locale));
    }

    private String resolveLegacyMessage(final String message, final Locale locale) {
        if (!TextHelper.isNullOrWhiteSpace(message)) {
            return message;
        }

        return messageCatalog.getUserMessage(MessagesEnum.COMMON_UNEXPECTED_ERROR, locale);
    }
}
