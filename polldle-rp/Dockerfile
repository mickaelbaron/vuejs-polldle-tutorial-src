FROM nginx:stable-alpine
LABEL maintainer="Mickael BARON"
ARG script_name=build
COPY $script_name.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]