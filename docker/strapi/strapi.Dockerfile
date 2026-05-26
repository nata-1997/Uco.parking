# Imagen de desarrollo (npm run develop). Basado en:
# https://docs.strapi.io/cms/installation/docker
FROM node:22-alpine

RUN apk update && apk add --no-cache \
    build-base gcc autoconf automake zlib-dev libpng-dev bash vips-dev git

ARG NODE_ENV=development
ENV NODE_ENV=${NODE_ENV}

WORKDIR /opt/

# package-lock.json es opcional la primera vez (create-strapi-app puede no generarlo hasta npm install).
COPY package.json package-lock.json* ./

RUN npm install -g node-gyp \
    && npm config set fetch-retry-maxtimeout 600000 -g \
    && if [ -f package-lock.json ]; then npm ci; else npm install; fi

ENV PATH=/opt/node_modules/.bin:$PATH

WORKDIR /opt/app

COPY . .

RUN chown -R node:node /opt/app

USER node

EXPOSE 1337

CMD ["npm", "run", "develop"]
