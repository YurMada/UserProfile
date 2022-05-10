FROM eclipse-temurin:17.0.2_8-jre-alpine
COPY ./target/user-service.jar /user-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "user-service.jar"]