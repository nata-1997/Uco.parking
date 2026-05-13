package co.edu.uco.ucoparking.crossscutting.messagescatalog;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class SpringMessageCatalog implements MessageCatalog {

    private final MessageSource messageSource;

    public SpringMessageCatalog(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getUserMessage(final MessagesEnum messageCode, final Locale locale, final Object... args) {
        return messageSource.getMessage(messageCode.getCode(), args, locale);
    }

    @Override
    public String getTechnicalMessage(final MessagesEnum messageCode, final Locale locale, final Object... args) {
        return messageSource.getMessage(messageCode.getTechnicalMessageKey(), args, locale);
    }
}
