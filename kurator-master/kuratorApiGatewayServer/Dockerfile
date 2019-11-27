# FROM openjdk:11
# EXPOSE 8080
# ADD /target/kuratorApiGatewayServer-0.0.1-SNAPSHOT.jar zuul.jar
# ENTRYPOINT [ "java", "-jar", "zuul.jar" ]
FROM maven:3.6-jdk-11 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn -f pom.xml clean package

FROM openjdk:11
EXPOSE 8080
COPY --from=build /target/kuratorApiGatewayServer-2.1.8.RELEASE.jar gateway.jar
ENTRYPOINT ["java","-jar","gateway.jar"]
