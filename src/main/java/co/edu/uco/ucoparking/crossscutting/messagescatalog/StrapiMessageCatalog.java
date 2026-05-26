package co.edu.uco.ucoparking.crossscutting.messagescatalog;

import co.edu.uco.ucoparking.infrastructure.strapi.StrapiCatalogService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Resuelve textos de error API desde Strapi (colección configurada en {@code strapi.collection-api-messages}).
 */
@Service
@Primary
public class StrapiMessageCatalog implements MessageCatalog {

    private final StrapiCatalogService strapiCatalogService;

    public StrapiMessageCatalog(final StrapiCatalogService strapiCatalogService) {
        this.strapiCatalogService = strapiCatalogService;
    }

    @Override
    public String getUserMessage(final MessagesEnum messageCode, final Locale locale, final Object... args) {
        final StrapiCatalogService.ApiMessageRow row = strapiCatalogService.getApiRow(messageCode.getCode());
        if (row == null || row.textoUsuario().isBlank()) {
            return messageCode.getCode();
        }
        return applyArgs(row.textoUsuario(), args);
    }

    @Override
    public String getTechnicalMessage(final MessagesEnum messageCode, final Locale locale, final Object... args) {
        final StrapiCatalogService.ApiMessageRow row = strapiCatalogService.getApiRow(messageCode.getCode());
        if (row == null || row.textoTecnico().isBlank()) {
            return messageCode.getTechnicalMessageKey();
        }
        return applyArgs(row.textoTecnico(), args);
    }

    private static String applyArgs(final String template, final Object... args) {
        if (args == null || args.length == 0) {
            return template;
        }
        String out = template;
        for (int i = 0; i < args.length; i++) {
            out = out.replace("{" + i + "}", String.valueOf(args[i]));
        }
        return out;
    }
}
