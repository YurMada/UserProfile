FROM maven:3.8.5-openjdk-17-slim as build
COPY ./ /src
RUN mvn -f /src/pom.xml clean package

<<<<<<< HEAD
FROM eclipse-temurin:17-jre
COPY --from=build /src/target/user-service.jar /usr/src/userservice/
WORKDIR usr/src/imagestorage
EXPOSE 8080
ENTRYPOINT ["java","-jar","user-service.jar"]
=======
FROM openjdk:17
COPY --from=build /src/target/user-service.jar /src/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "src/user-service.jar"]
>>>>>>> 87903bf8b1fe069164f92e84f0bde492091e7a7f
