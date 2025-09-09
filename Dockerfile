FROM openjdk:17-jdk-slim

RUN apt update && apt install -y git

COPY build/libs/myshop-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

ENV SPRING_PROFILES_ACTIVE=docker