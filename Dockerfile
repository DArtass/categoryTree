FROM maven:3.8.3-jdk-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
COPY src/main/resources/application.properties .
EXPOSE 8075
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]