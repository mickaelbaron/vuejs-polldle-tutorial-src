# Build env
FROM node:lts-alpine as build-npm-stage
ARG script_name=build
LABEL maintainer="Mickael BARON"

WORKDIR /polldle-vue
COPY package*.json ./
RUN npm install
COPY public ./public
COPY src ./src
COPY vue.config.js ./
COPY .env.production ./
COPY .env.subdomain ./

# RUN echo $use_subdomain && if [ "$use_subdomain" == "true" ]; then npm run subdomain ; else npm run build ; fi

RUN npm run $script_name

# Run env
FROM nginx:stable-alpine
COPY --from=build-npm-stage /polldle-vue/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]