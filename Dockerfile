FROM maven:3.8.3-openjdk-17 AS build
FROM openjdk:17
RUN mvn -f trivago_case_study/pom.xml clean package
COPY /target/trivago_case_study-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080