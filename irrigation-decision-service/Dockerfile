FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests -pl irrigation-decision-service -am

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/irrigation-decision-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
