# PollDLE (PollDLE ~= Poll + the last part of DooDLE)

Voting done simply in real-time with Polldle. PollDLE ~= Poll + the last part of Doo DLE (an Internet calendar tool for time management, famous for its simplification)

Some technicals concepts in this application:

* front-end in Vue.js and Vue CLI,
* back-end in Java with MicroProfile and KumuluzEE implementation (JAX-RS and CDI),
* using the given/when/then style for Java unit test,
* full build with Docker with multi-stage build feature,
* deployement with Docker.

All instructions to understand how to develop a front part with [Vue.js](https://vuejs.org/) can be found here (in french): https://mickael-baron.fr/web/vuejs-miseenoeuvre-part2 and https://mickael-baron.fr/web/vuejs-deploiement-part3 

## Software requirements

* Docker (that's all)

## Platform

Polldle building and deployement have been tested on:

* macOS Catalina (with Docker for Mac),
* Linux Ubuntu.

In fact, all operating systems that support Docker can build and test PollDLE.

## Download

Clone the latest development source:

```console
git clone https://github.com/mickaelbaron/vuejs-polldle-tutorial-src
```

## Configure

In the case you do not need a subpath, go to the next section.

If you want to use a subpath to deploy PollDLE (i.e. <http://localhost/YOUR_SUBPATH>), edit the *subpath.conf* and replace all `YOUR_SUBPATH` string by your preferred value (i.e. `polldle`).

```yaml
server {
    listen 80;

    location /YOUR_SUBPATH/server/ {
        proxy_pass http://backend:9991/;
        proxy_http_version 1.1;
        proxy_set_header Connection "";
    }

    location /YOUR_SUBPATH/ {
        rewrite ^/YOUR_SUBPATH(/.*)$ $1 break;
        proxy_pass http://frontend;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }
}
```

## Build

To build without subpath: <https://localhost>, execute this command line:

```console
docker-compose build --build-arg script_name=build
```

To build with subpath: <http://localhost/YOUR_SUBPATH>, execute this command line:

```console
docker-compose build  --build-arg script_name=subpath
```

To check if the images are been built, execute this command line:

```console
$ docker images
REPOSITORY                     TAG                 IMAGE ID            CREATED              SIZE
mickaelbaron/polldle-rp        latest              7c20a2289271        27 seconds ago       21.3MB
mickaelbaron/polldle-vue       latest              fd4edb630f77        28 seconds ago       22MB
mickaelbaron/polldle-backend   latest              3fbc76dd4c2a        About a minute ago   165MB
...
```

## Run

From the root of the project, execute this command line:

```console
docker-compose up -d
```

Open your preferred web browser and go to this url: <http://localhost> (without subpath) or <http://localhost/YOUR_SUBPATH> (with a subpath).
