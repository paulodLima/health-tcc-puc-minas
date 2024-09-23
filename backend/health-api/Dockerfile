FROM ubuntu:latest
LABEL authors="paulos"

FROM maven:3.9.4-eclipse-temurin-21

WORKDIR /app

RUN mvn  clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

COPY target/health-0.0.1-SNAPSHOT.jar health.jar

ENTRYPOINT ["java", "-jar", "health.jar"]

EXPOSE 8081