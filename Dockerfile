# Usar una imagen base para Java
FROM openjdk:11-jdk-slim

# Crear un directorio de trabajo
WORKDIR /app

# Copiar archivos de construcción al contenedor
COPY target/Api-0.0.1-SNAPSHOT.jar /app/Api-0.0.1-SNAPSHOT.jar

# Exponer el puerto en el que corre la aplicación (ajusta según tu configuración)
EXPOSE 9090

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "Api-0.0.1-SNAPSHOT.jar"]