FROM gradle:8.13-jdk21 AS builder
WORKDIR /app
COPY . .
COPY gradle ./gradle
RUN gradle build -x test || return 0
COPY . .
RUN gradle bootJar -x test

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]