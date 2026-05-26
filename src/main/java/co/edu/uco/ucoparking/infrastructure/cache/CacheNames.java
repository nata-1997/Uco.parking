package co.edu.uco.ucoparking.infrastructure.cache;

/**
 * Nombres de regiones de caché (Valkey/Redis). Centralizados para evitar typos y alinear TTL en {@link co.edu.uco.ucoparking.infrastructure.config.CacheConfig}.
 */
public final class CacheNames {

    public static final String ID_TYPES = "idTypes";
    public static final String ACADEMIC_PROGRAMS = "academicPrograms";

    private CacheNames() {
    }
}
