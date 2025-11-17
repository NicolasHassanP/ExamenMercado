# ETAPA 1: BUILD
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

COPY . /workspace
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# ETAPA 2: RUNTIME
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8080

# Copiar cualquier JAR generado y renombrarlo a app.jar
# Usa wildcard para evitar errores por versión/patch del nombre
COPY --from=build /workspace/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
