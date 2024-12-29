FROM openjdk:22-ea-19-jdk-slim AS build

COPY /target/Gateway-3.2.4.jar gateway.jar
EXPOSE 80

ENTRYPOINT ["java","-jar","gateway.jar"]