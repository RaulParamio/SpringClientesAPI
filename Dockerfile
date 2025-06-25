# Etapa 1: Construcción
FROM maven:3.8.4-openjdk-17-slim AS builder

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos los archivos pom.xml y codigo fuente del proyecto
COPY pom.xml .
COPY src ./src

#Ejecutamos Maven para limpiar y compilar el proyecto, empaquetándolo en un .jar, y saltamos los tests para acelerar
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final para producción
FROM openjdk:17-jdk-slim

# Directorio de ejecución
WORKDIR /app

# Copiamos el .jar generado en la etapa de construcción
COPY --from=builder /app/target/spring-clientes-api-0.0.1-SNAPSHOT.jar app.jar

# Puerto por donde escuchará Spring Boot
EXPOSE 8080

# Comando que se ejecutará al iniciar el contenedor: lanzar la aplicación Java desde el .jar
ENTRYPOINT ["java", "-jar", "app.jar"]

LABEL authors="Raul"