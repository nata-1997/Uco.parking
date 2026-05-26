import type { Core } from '@strapi/strapi';

const config = ({ env }: Core.Config.Shared.ConfigParams): Core.Config.Plugin => ({
  'strapi-import-export': {
    enabled: true,
    config: {
      serverPublicHostname: env('STRAPI_PUBLIC_URL', 'http://127.0.0.1:1337'),
    },
  },
});

export default config;
