# Build-Stage: Jar mit Gradle bauen
FROM gradle:8.10-jdk21-alpine AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle bootJar --no-daemon

# Runtime-Stage: Leichtes JDK-Image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]