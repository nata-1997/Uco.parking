package co.edu.uco.ucoparking.crossscutting.messagescatalog;

import java.util.Locale;

public interface MessageCatalog {

    String getUserMessage(MessagesEnum messageCode, Locale locale, Object... args);

    String getTechnicalMessage(MessagesEnum messageCode, Locale locale, Object... args);
}
