FROM openjdk:17-jdk-slim-buster


COPY target/*.jar app/app.jar

WORKDIR /app
ENTRYPOINT java -jar app.jar