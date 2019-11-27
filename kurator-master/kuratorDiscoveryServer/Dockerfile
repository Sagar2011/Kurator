FROM maven:3.6-jdk-11 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn -f pom.xml clean package

FROM openjdk:11
EXPOSE 8761
COPY --from=build /target/kuratorDiscoveryServer-2.1.8.RELEASE.jar eureka.jar
ENTRYPOINT ["java","-jar","eureka.jar"]


# FROM openjdk:11
# EXPOSE 8761:8761
# ADD /target/kuratorDiscoveryServer-0.0.1-SNAPSHOT.jar eureka.jar
# ENTRYPOINT ["java","-jar","eureka.jar"]
