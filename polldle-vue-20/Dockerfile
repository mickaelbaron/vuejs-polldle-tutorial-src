FROM node:lts-alpine as build-npm-stage
LABEL maintainer="Mickael BARON"
# Use ARG instruction to create script_name variable
ARG script_name=build

WORKDIR /polldle-vue
COPY package*.json ./
RUN npm install
COPY public ./public
COPY src ./src
COPY .env.production ./

# Inject script_name variable into the command
# RUN npm run build
RUN npm run $script_name

# Run env
FROM nginx:stable-alpine
COPY --from=build-npm-stage /polldle-vue/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]