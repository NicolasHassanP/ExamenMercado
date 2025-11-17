# ETAPA 1: BUILD
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

# Copiar todo y dar permiso al gradle wrapper (si usas gradlew)
COPY . .
RUN chmod +x ./gradlew

# Generar el fat JAR con Spring Boot
RUN ./gradlew bootJar --no-daemon

# ETAPA 2: RUNTIME
FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080

# Ajusta el nombre del JAR según lo que realmente genere tu build
# Ejemplo aquí: build/libs/ExamenMercado-0.0.1-SNAPSHOT.jar
COPY --from=build ./build/libs/ExamenMercado-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
