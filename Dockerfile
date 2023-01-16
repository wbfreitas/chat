FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY target/*.jar app/app.jar

ENTRYPOINT java -jar app.jar