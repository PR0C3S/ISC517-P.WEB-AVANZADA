# Aplicación Prueba JMS - ActiveMQ
Software para probar las colas de publicación y subcripción 
con el servidor Apache ActiveMQ.

## Ejecutar:
* ./gradlew task shadowjar
* java -jar practica4-publicador.jar 1 //**Cliente 2 para publicar mensaje - Cola Topic**
* java -jar practica4-publicador.jar 2 //**Cliente 2 para publicar mensaje - Cola Topic**

En caso de utilizar ActiveMQ de forma independiente pueden utilizar la siguiente imagen de docker:

docker pull rmohr/activemq

La documentación de la imagen [https://hub.docker.com/r/rmohr/activemq] 
