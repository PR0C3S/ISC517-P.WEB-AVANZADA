package com.pucmm.practica4_publicador.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by vacax on 04/10/15.
 */
public class Productor {

    public Productor(){

    }


    public void enviarMensaje(String mensajeEnviar) throws JMSException {

        //Creando el connection factory indicando el host y puerto, en la trama el failover indica que reconecta de manera
        // automatica
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        //Crea un nuevo hilo cuando hacemos a conexión, que no se detiene cuando
        // aplicamos el metodo stop(), para eso tenemos que cerrar la JVM o
        // realizar un close().
        Connection connection = factory.createConnection("admin", "admin");
        connection.start();

        // Creando una sesión no transaccional y automatica.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Creamos o nos connectamos a la una cola, por defecto ActiveMQ permite
        // la creación si no existe. La cola es del tipo topic es acumula los mensajes es en el momento.
        MessageProducer producer =  null;
        Topic topic = session.createTopic("notificacion_sensores");
        producer = session.createProducer(topic);
        String mensaje = mensajeEnviar;

        // Creando un simple mensaje de texto y enviando.
        TextMessage message = session.createTextMessage(mensaje);
        producer.send(message);

        //Desconectando la referencia.
        producer.close();
        session.close();
        connection.stop();
    }
}
