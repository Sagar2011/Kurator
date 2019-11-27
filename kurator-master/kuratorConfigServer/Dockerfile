# FROM openjdk:11
# EXPOSE 8888:8888
# ADD /target/kuratorConfigServer-0.0.1-SNAPSHOT.jar config-server.jar
# ENTRYPOINT ["java","-jar","config-server.jar"]
FROM maven:3.6-jdk-11 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn -f pom.xml clean package

FROM openjdk:11
EXPOSE 8888
COPY --from=build /target/kuratorConfigServer-2.1.8.RELEASE.jar config.jar
ENTRYPOINT ["java","-jar","config.jar"]
