# Build env
FROM maven:3-jdk-8 AS build-java-stage
LABEL maintainer="Mickael BARON"
WORKDIR /polldle
COPY polldle-api polldle-api
COPY polldle-server polldle-server
COPY pom.xml .
RUN mvn -f pom.xml clean package

# Run env
FROM openjdk:8-jre-slim
COPY --from=build-java-stage /polldle/polldle-server/target/classes /polldle/classes
COPY --from=build-java-stage /polldle/polldle-server/target/dependency/*.jar /polldle/dependency/
EXPOSE 9991
ENTRYPOINT ["java", "-cp", "/polldle/classes:/polldle/dependency/*", "com.kumuluz.ee.EeApplication"]