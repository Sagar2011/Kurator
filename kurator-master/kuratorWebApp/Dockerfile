# FROM openjdk:11
# EXPOSE 8200:8200
# ADD /target/kuratorWebApp-0.0.1-SNAPSHOT.jar kurator-ui-service.jar
# ENTRYPOINT [ "java", "-jar", "kurator-ui-service.jar" ]
# ----------------------------#
#        JAVA DEPENDENCY      #
# ----------------------------#
FROM maven:3.6-jdk-11 as webapp_server_maven_cache

RUN mkdir /webappserver

WORKDIR /webappserver

COPY pom.xml /webappserver/

# Expected to download dependencies but not build
# dependencies will be in /root/.m2
RUN mvn dependency:go-offline

# ----------------------------#
#         UI BUILD            #
# ----------------------------#
FROM node:12-alpine as webapp_build

RUN mkdir /webapp

WORKDIR /webapp

COPY kurator-ui/package.json /webapp/
# Webapp
RUN npm install

COPY ./kurator-ui /webapp/

RUN ls -ltr /webapp

# build the UI
RUN npm run build -- --prod

RUN ls -ltr ../

RUN ls -ltr .

RUN ls -ltr /webapp

# ----------------------------#
#        JAVA BUILD           #
# ----------------------------#
FROM maven:3.6-jdk-11 as webapp_server_build

RUN mkdir /webappserver

WORKDIR /webappserver

COPY pom.xml /webappserver/

# Expected to download dependencies but not build
# RUN mvn dependency:go-offline

# Now copy actual source, i.e., this line should be after downloading dependencies to avoid repeated download when src changes
COPY ./src /webappserver/src

RUN rm -rf /webappserver/src/main/resources/static

RUN mkdir -p /webappserver/src/main/resources/static

# Now copy maven dependencies cached from earlier stage
COPY --from=webapp_server_maven_cache /root/.m2 /root/.m2

# Now copy webapp (webclient) distribution for packaging with JAR
COPY --from=webapp_build /webapp/dist/ /webappserver/src/main/resources/static/

# Build now, which should happen quick
RUN mvn clean package -DskipTests=true

# If you choose to test it in a debug mode, this will be the end of Dockerfile
# ENTRYPOINT ["mvn", "spring-boot:run"]

# ----------------------------#
#        JAVA Service         #
# ----------------------------#
FROM maven:3.6-jdk-11

# name this basis the micro service name
RUN mkdir /webappserver

WORKDIR /webappserver

# Copy the built service jars and resources
COPY --from=webapp_server_build /webappserver/target /webappserver/target

RUN ls -ltr /webappserver/
# kuratorWebApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "target/kuratorWebApp-2.1.8.RELEASE.jar"]
