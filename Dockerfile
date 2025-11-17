# ========================================
# ETAPA 1: BUILD (Compilación - Usando JDK)
# ========================================
# Usamos openjdk:17-jdk-bullseye, que es una etiqueta estable y explícita para el JDK.
FROM openjdk:17-jdk-bullseye AS build

# Copiar TODO el código fuente del proyecto al contenedor
COPY . .

# Dar permisos de ejecución al script gradlew
RUN chmod +x ./gradlew

# Ejecutar Gradle para compilar y generar el JAR ejecutable
RUN ./gradlew bootJar --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución - Usando JRE Ligero)
# ========================================
# Usamos openjdk:17-jre-bullseye para el runtime, reduciendo el tamaño final.
FROM openjdk:17-jre-bullseye

# Documentar que la aplicación escucha en el puerto 8080
EXPOSE 8080

# Copiar el JAR generado en la ETAPA 1 (build) a la imagen final
# NOTA: Confirma que este nombre de JAR sea el correcto.
COPY --from=build ./build/libs/ExamenMercado-0.0.1-SNAPSHOT.jar ./app.jar

# Comando que se ejecuta cuando el contenedor inicia
ENTRYPOINT ["java", "-jar", "app.jar"]