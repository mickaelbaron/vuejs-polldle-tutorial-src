# PollDLE (PollDLE ~= Poll + the last part of DooDLE)

Voting done simply in real-time with Polldle. PollDLE ~= Poll + the last part of Doo DLE (an Internet calendar tool for time management, famous for its simplification)

Some technicals concepts in this application:

* front-end in Vue.js 3 and Vite,
* back-end in Java with MicroProfile and OpenLiberty implementation (JAX-RS and CDI),
* using the given/when/then style for Java unit test,
* full build with Docker with multi-stage build feature,
* deployement with Docker.

> All instructions to understand how to develop a front part with [Vue.js](https://vuejs.org/) can be found here (in french): https://mickael-baron.fr/web/vuejs-miseenoeuvre-part2 and https://mickael-baron.fr/web/vuejs-deploiement-part3 

## Software requirements

* Docker (that's all)

## Platform

Polldle building and deployement have been tested on:

* macOS Tahoe (with Docker for Mac),
* Linux Ubuntu.

In fact, all operating systems that support Docker can build and test PollDLE.

## Download

Clone the latest development source:

```bash
git clone https://github.com/mickaelbaron/vuejs-polldle-tutorial-src
```

## Configure

In the case you do not need a subpath, go to the next section.

If you want to use a subpath to deploy PollDLE (i.e. <http://localhost/YOUR_SUBPATH>), edit the *subpath.conf* and replace all `YOUR_SUBPATH` string by your preferred value (i.e. `polldle`).

```yaml
server {
    listen 80;

    location /YOUR_SUBPATH/server/ {
        proxy_pass http://backend:9080/;
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

```bash
docker compose build --build-arg script_name=build
```

To build with subpath: <http://localhost/YOUR_SUBPATH>, execute this command line:

```bash
docker compose build  --build-arg script_name=subpath
```

To check if the images are been built, execute this command line:

```bash
docker compose ps
```

The expected console output:

```bash
NAME                                    IMAGE                          COMMAND                  SERVICE    CREATED          STATUS          PORTS
vuejs-polldle-tutorial-src-backend-1    mickaelbaron/polldle-backend   "/opt/ol/helpers/run…"   backend    24 seconds ago   Up 24 seconds   0.0.0.0:9080->9080/tcp, [::]:9080->9080/tcp
vuejs-polldle-tutorial-src-frontend-1   mickaelbaron/polldle-vue       "nginx -g 'daemon of…"   frontend   24 seconds ago   Up 24 seconds   80/tcp
vuejs-polldle-tutorial-src-rp-1         mickaelbaron/polldle-rp        "nginx -g 'daemon of…"   rp         24 seconds ago   Up 23 seconds   0.0.0.0:80->80/tcp, [::]:80->80/tcp
```

## Run

From the root of the project, execute this command line:

```bash
docker compose up -d
```

Open your favorite web browser and go to this url: <http://localhost> (without subpath) or <http://localhost/YOUR_SUBPATH> (with a subpath).
