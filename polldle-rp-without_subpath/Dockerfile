FROM nginx:stable-alpine
LABEL maintainer="Mickael BARON"
COPY build.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]