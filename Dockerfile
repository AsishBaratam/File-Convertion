# Stage 1: Build
FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:21-jdk-slim
COPY --from=build /app/target/practice-0.0.1-SNAPSHOT.jar practice.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "practice.jar"]
