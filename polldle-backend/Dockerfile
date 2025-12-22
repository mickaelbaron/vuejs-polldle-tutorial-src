# Build env
FROM maven:3.9.11-eclipse-temurin-11 AS build-java-stage
LABEL maintainer="Mickael BARON"

WORKDIR /polldle
COPY polldle-api polldle-api
COPY polldle-server polldle-server
COPY pom.xml .

RUN mvn -f pom.xml clean package

# Run env
FROM openliberty/open-liberty:kernel-slim-java11-openj9-ubi-minimal

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

LABEL \
    org.opencontainers.image.authors="Mickael BARON" \
    org.opencontainers.image.url="local" \
    org.opencontainers.image.source="https://github.com/mickaelbaron/vuejs-polldle-tutorial-src" \
    org.opencontainers.image.version="$VERSION" \
    org.opencontainers.image.revision="$REVISION" \
    vendor="Mickael BARON" \
    name="rest" \
    version="$VERSION-$REVISION" \
    summary="The backend microservice of Polldle" \
    description="This image contains the backend microservice of Polldle running with the Open Liberty runtime."

COPY --chown=1001:0 --from=build-java-stage /polldle/polldle-server/src/main/liberty/config/server.xml /config/server.xml
COPY --chown=1001:0 --from=build-java-stage /polldle/polldle-server/target/polldle-server.war /config/apps/
    
RUN features.sh

RUN configure.sh