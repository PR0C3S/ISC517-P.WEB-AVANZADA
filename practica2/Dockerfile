# Archivo que representa la información para la creación de
# una imagen para Docker, indicando todos los parametros necesarios.

FROM ubuntu
#Creando al variable de ambiente necesario para evitar la configuración del Timezone de la librería tzdata.
# Tomado de https://rtfm.co.ua/en/docker-configure-tzdata-and-timezone-during-build/
ENV TZ=America/Santo_Domingo
#Creando los archivos necesarios.
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# Ejecutando la instalación de apache.
RUN apt update && apt install -y apache2 && service apache2 start
# Moviendo el archivos a la ruta por defecto para apache.
COPY index.html /var/www/html/

# Concepto de Multi-stage.
FROM gradle:7.2.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:11.0.12-jre-slim-buster

LABEL maintainer="Grupo10 ISC"

# Indicando variable de ambiente para pasar el nombre
# de la base de datos.
ENV NOMBRE_APP = 'practica2'
# forma estandar para cambiar la configuracion
ENV DB_NAME=practica2
# Puedo pasar las variables directas que serán tomadas por Spring Boot.
ENV spring.datasource.username='sa'
ENV spring.datasource.password=''

# Añadiendo el punto de montaje en el host
# Por defecto Tomcat crea los archivo temporales en esa ruta,
# lo habilitamos para ver los log, no es necesario para nuestro ejemplo.
VOLUME /tmp

# Puertos que estarán disponibles de nuestra aplicación.
EXPOSE 8080

# creando la carpeta para el proyecto
RUN mkdir /app

#Comando que se ejecuta una vez es iniciada la aplicación.
# desde la otra instancia estaremos copiando lo necesario
COPY --from=build /home/gradle/src/build/libs/*.jar /app/practica2-app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/practica2-app.jar"]

#FROM ibmcom/curl:4.0.0



FROM openjdk:11.0.12-jre-slim-buster

ENV RELEASE_DATE 2017-04-23
ENV H2DATA /h2-data

RUN apt-get update

RUN apt-get update && apt-get install -y \
curl

RUN apt-get update && apt-get install -y \
unzip

RUN curl http://www.h2database.com/h2-$RELEASE_DATE.zip -o h2.zip \
    && unzip h2.zip -d . \
    && rm h2.zip

RUN ln -s $(ls /h2/bin/*jar) /h2/bin/h2.jar

RUN mkdir /docker-entrypoint-initdb.d

VOLUME /h2-data

EXPOSE 8082

COPY docker-entrypoint.sh /usr/local/bin/
ENTRYPOINT ["docker-entrypoint.sh"]

CMD java -cp /h2/bin/h2.jar org.h2.tools.Server \
  -web -webAllowOthers -tcp -tcpAllowOthers -baseDir $H2DATA