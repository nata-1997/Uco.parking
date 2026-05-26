package co.edu.uco.ucoparking.initializer;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "co.edu.uco")
@EntityScan(basePackages = "co.edu.uco.ucoparking.infrastructure.persistence.sql.entity")
@EnableJpaRepositories(basePackages = "co.edu.uco.ucoparking.infrastructure.persistence.sql")
public class UcoParkingApplication {

    public static void main(String[] args) {
        loadOptionalEnvFile();
        SpringApplication.run(UcoParkingApplication.class, args);
    }

    /**
     * Si existe {@code .env} en la raíz del proyecto (p. ej. exportado desde Infisical), inyecta variables
     * como propiedades del sistema para que {@code application.yml} resuelva {@code ${VAR}}.
     * No sobrescribe variables ya definidas en el SO ni propiedades {@code -D} del JVM.
     */
    private static void loadOptionalEnvFile() {
        try {
            final Dotenv dotenv = Dotenv.configure()
                    .directory("./")
                    .ignoreIfMissing()
                    .load();
            dotenv.entries().forEach(e -> {
                final String key = e.getKey();
                final String val = e.getValue();
                if (val == null || val.isBlank()) {
                    return;
                }
                if (System.getenv(key) != null) {
                    return;
                }
                if (System.getProperty(key) == null) {
                    System.setProperty(key, val);
                }
            });
        } catch (Exception ignored) {
            // Sin .env o archivo ilegible: se usan solo env del sistema / Run Configuration / secrets.
        }
    }
}
