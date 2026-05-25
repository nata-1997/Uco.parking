# ModSecurity (WAF) delante de Kong

La configuración activa del contenedor se define en **`docker-compose-kong.yml`** (servicio `modsecurity-waf`, perfil `waf`) y en [docs/MODSECURITY_UCOPARKING.md](../../docs/MODSECURITY_UCOPARKING.md).

Esta carpeta sirve para **ampliaciones opcionales** (por ejemplo reglas CRS personalizadas o exclusiones) que montes como volúmenes según la documentación de [modsecurity-crs-docker](https://github.com/coreruleset/modsecurity-crs-docker).
