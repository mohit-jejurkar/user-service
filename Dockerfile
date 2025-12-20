FROM eclipse-temurin:17-jre-jammy
WORKDIR /user-service
COPY build/libs/*.jar user-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","user-service-0.0.1-SNAPSHOT.jar","--spring.profiles.active=docker"]
