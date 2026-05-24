# Paquetes: Clean Architecture + Hexagonal (UcoParking)

## ¿Estaba bien `application.application`?

**No.** El doble `application` era redundante y confuso: la capa de aplicación del caso de uso ya vive bajo `features.<contexto>.application`. No hace falta repetir el segmento.

Estructura corregida (una sola carpeta `application` por feature):

```text
co.edu.uco.ucoparking
├── application/                    # Contratos genéricos mínimos (InputPort, UseCase) — “shared kernel” ligero
├── crossscutting/                  # Transversal: mensajes, excepciones, helpers, especificaciones
├── features/
│   ├── parking/parkingspot/
│   │   ├── application/            # Casos de uso del agregado cupo: inputport, usecase, DTOs de entrada
│   │   └── ParkingSpotStoredStatus.java
│   └── student/registernewstudent/
│       └── application/            # Caso de uso registrar estudiante
└── infrastructure/                 # Adaptadores: controladores HTTP, JPA, repositorios, bootstrap
```

## Mapeo hexagonal (resumen)

| Capa | Paquetes en este proyecto | Rol |
|------|---------------------------|-----|
| **Dominio / aplicación** | `features.*.application` | Puertos de entrada (`*InputPort`), casos de uso (`*UseCase`, `impl`), comandos/consultas (`*Domain`, `*InputTO`), validadores |
| **Adaptadores de entrada** | `infrastructure.persistence.controler` | REST (`*Controller`) — llaman a los *InputPort* |
| **Adaptadores de salida** | `infrastructure.persistence.repository`, `sql`, `mapper` | Persistencia JPA, adaptadores de repositorio |
| **Transversal** | `crossscutting` | Errores, catálogo de mensajes, utilidades compartidas |

## Convención por feature

- **`features.<subdominio>.<caso_o_agregado>`**: agrupa todo lo específico del negocio (aquí `parkingspot` y `registernewstudent`).
- **`...application.inputport`**: API que expone el caso de uso hacia “afuera” (controladores, otros servicios).
- **`...application.usecase`**: implementación del caso de uso y modelos de comando del caso de uso.

## Mejoras opcionales (no hechas aún)

- Extraer **entidades de dominio puras** fuera de `infrastructure` si se quiere un dominio sin dependencia de JPA.
- Renombrar `co.edu.uco.ucoparking.application` a algo como `shared.application` para dejar claro que no es la capa de un feature.
- Sustituir dependencias de `infrastructure.persistence.entity` desde la capa `application` por interfaces en `application` o `domain` (dependencia hacia adentro).
