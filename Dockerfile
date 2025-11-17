# ========================================
# ETAPA 1: BUILD (Compilación - JDK Completo)
# ========================================
# Usamos openjdk:17 (base Debian) que ya incluye el JDK.
FROM openjdk:17 AS build

# ELIMINAMOS los comandos de instalación de Alpine (apk), ya que la base es Debian/Bullseye.

# Copiar TODO el código fuente del proyecto al contenedor
COPY . .

# Dar permisos de ejecución al script gradlew
RUN chmod +x ./gradlew

# Ejecutar Gradle para compilar y generar el JAR ejecutable
# El --no-daemon es esencial para la eficiencia en Docker.
RUN ./gradlew bootJar --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución - JRE Ligero)
# ========================================
# Usamos openjdk:17-jre (el runtime) para reducir el tamaño final de la imagen.
FROM openjdk:17-jre

# Documentar que la aplicación escucha en el puerto 8080
EXPOSE 8080

# Copiar el JAR generado desde la etapa de build
# NOTA: Asegúrate que el nombre del JAR generado sea correcto.
COPY --from=build ./build/libs/ExamenMercado-0.0.1-SNAPSHOT.jar ./app.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]