FROM maven:3.8.5-openjdk-17-slim as build
COPY ./ /src
RUN mvn -f /src/pom.xml clean package

FROM eclipse-temurin:17-jre
COPY --from=build /src/target/user-service.jar /usr/src/userservice/
WORKDIR usr/src/imagestorage
EXPOSE 8080
ENTRYPOINT ["java","-jar","user-service.jar"]