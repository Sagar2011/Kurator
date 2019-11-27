# FROM openjdk:11
# EXPOSE 8097:8097
# ADD /target/kuratorSemanticService-0.0.1-SNAPSHOT.jar kurator-doc-service.jar
# ENTRYPOINT [ "java", "-jar", "kurator-doc-service.jar" ]
#kuratorDocumentService-0.0.1-SNAPSHOT.jar
FROM maven:3.6-jdk-11 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn -f pom.xml clean package -DskipTests=true


FROM openjdk:11
EXPOSE 8097
COPY --from=build /target/kuratorSemanticService-2.1.8.RELEASE.jar kurator-doc-service.jar
ENTRYPOINT ["java","-jar","kurator-doc-service.jar"]