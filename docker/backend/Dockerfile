# Imagen de producción del API Spring Boot (multi-stage).
# Build: desde la raíz del repo: docker build -f docker/backend/Dockerfile -t ucoparking-backend .
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY mvnw mvnw.cmd ./
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src
RUN sed -i 's/\r$//' mvnw 2>/dev/null || true \
    && chmod +x mvnw \
    && ./mvnw -B -DskipTests package \
    && mv target/uco.parking-*-SNAPSHOT.jar /app/app.jar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/app.jar /app/app.jar
EXPOSE 8080
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
