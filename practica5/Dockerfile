# El builder
# docker build -t znzn00/mock-api .
FROM gradle:7.2.0-jdk11 AS builder
WORKDIR /app
COPY / ./
RUN gradle task bootJar

# Real imagen
FROM openjdk:11.0.7-jre-slim-buster
RUN apt-get update && apt-get install -y curl
# Configuración de la aplicación
# Quien mantiene la versión.
LABEL maintainer="grupo10"
# Indicando variable de ambiente para pasar el nombre
# de la base de datos.
ENV NOMBRE_APP = 'practica5'
# forma estandar para cambiar la configuracion
ENV DB_NAME=practica5
# Puedo pasar las variables directas que serán tomadas por Spring Boot.
ENV spring.datasource.username='root'
ENV spring.datasource.password='john'
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

