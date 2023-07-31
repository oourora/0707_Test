FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/0707Test-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]

