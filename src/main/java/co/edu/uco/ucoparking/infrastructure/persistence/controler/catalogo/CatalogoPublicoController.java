package co.edu.uco.ucoparking.infrastructure.persistence.controler.catalogo;

import co.edu.uco.ucoparking.infrastructure.strapi.StrapiCatalogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Expone textos de interfaz cargados desde Strapi para el front (sin credenciales de Strapi en el navegador).
 */
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
@RestController
@RequestMapping("/api/v1/catalogo")
public class CatalogoPublicoController {

    private final StrapiCatalogService strapiCatalogService;

    public CatalogoPublicoController(final StrapiCatalogService strapiCatalogService) {
        this.strapiCatalogService = strapiCatalogService;
    }

    @GetMapping("/textos-ui")
    public Map<String, String> textosUi() {
        return strapiCatalogService.snapshotUi();
    }
}
