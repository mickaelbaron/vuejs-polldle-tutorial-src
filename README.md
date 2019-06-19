# PollDLE (Polldle ~= Poll + the last part of DooDLE)

Voting done simply in real-time with Polldle. Polldle ~= Poll + the last part of Doo DLE (an Internet calendar tool for time management, famous for its simplification)

Some technicals concepts in this application:

* front-end in Vue.js 2.x and Vue CLI 3.y,
* back-end in Java with MicroProfile 2.1 and KumuluzEE implementation (JAX-RS and CDI),
* using the given/when/then style for Java unit test,
* full build with Docker,
* deployement with Docker.

## Software requirements

* Docker (that's all)

## Platform

Polldle building and deployement have been tested on:

* macOS Sierra (with Docker for Mac),
* Linux Ubuntu.

In fact, all operating systems that support Docker can build and test PollDLE.

## Download

Clone the latest development source:

```console
git clone https://github.com/mickaelbaron/vuejs-polldle-tutorial-src
```

## Configure

If you want to use a subdomain to deploy PollDLE (i.e. <http://localhost/YOUR_SUBDOMAIN>), edit the *subdomain.conf* and replace all `YOUR_SUBDOMAIN` string by your preferred value (i.e. `polldle`).

```yaml
server {
    listen 80;

    location /YOUR_SUBDOMAIN/server/ {
        proxy_pass http://backend:9991/;
        proxy_http_version 1.1;
        proxy_set_header Connection "";
    }

    location /YOUR_SUBDOMAIN/ {
        rewrite ^/YOUR_SUBDOMAIN(/.*)$ $1 break;
        proxy_pass http://frontend;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }
}
```

In the case or you do not need a subdomain, go on next section.

## Build

To build without subdomain: <http://www.mydomain.com>, execute this command line:

```console
docker-compose build --build-arg script_name=build
```

To build with subdomain: <http://www.mydomain.com/subdomain>, execute this command line:

```console
docker-compose build  --build-arg script_name=subdomain
```

To check if the images are been built, execute this command line:

```console
$ docker images
REPOSITORY                     TAG                 IMAGE ID            CREATED             SIZE
mickaelbaron/polldle-backend   latest              890ee2940a5c        44 minutes ago      182MB
mickaelbaron/polldle-rp        latest              8de5e344ae15        9 days ago          20.4MB
mickaelbaron/polldle-vue       latest              5d4359c80480        9 days ago          22.1MB
...
```

## Run

From the root of the project, execute this command line:

```console
docker-compose up -d
```

Open your preferred web browser and go to this url: <http://localhost> (without subdomain) or <http://localhost/YOUR_SUBDOMAIN> (with a subdomain).
