# syntax=docker/dockerfile:1
# Contexto principal: raíz del repo del BACK (para copiar nginx).
# Contexto nombrado "uco_front": repo del FRONT (Compose: additional_contexts).
# Si package.json no está en la raíz de ese contexto, define UCO_FRONT_SUBPATH (p. ej. "UcoParking" o "apps/web").
FROM node:22-alpine AS build
ARG UCO_FRONT_SUBPATH=.
WORKDIR /app
COPY --from=uco_front ${UCO_FRONT_SUBPATH}/package.json ./package.json
COPY --from=uco_front ${UCO_FRONT_SUBPATH}/package-lock.json* ./
RUN if [ -f package-lock.json ]; then npm ci --no-audit --fund=false; else npm install --no-audit --fund=false; fi
COPY --from=uco_front ${UCO_FRONT_SUBPATH}/ ./
ARG VITE_AUTH0_DOMAIN=
ARG VITE_AUTH0_CLIENT_ID=
ARG VITE_AUTH0_AUDIENCE=
ARG VITE_API_BASE_URL=
ENV VITE_AUTH0_DOMAIN=$VITE_AUTH0_DOMAIN \
    VITE_AUTH0_CLIENT_ID=$VITE_AUTH0_CLIENT_ID \
    VITE_AUTH0_AUDIENCE=$VITE_AUTH0_AUDIENCE \
    VITE_API_BASE_URL=$VITE_API_BASE_URL
RUN npm run build

FROM nginx:1.27-alpine
COPY docker/frontend/nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
