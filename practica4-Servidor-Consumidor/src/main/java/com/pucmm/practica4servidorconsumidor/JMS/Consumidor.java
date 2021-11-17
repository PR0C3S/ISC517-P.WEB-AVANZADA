package com.pucmm.practica4servidorconsumidor.JMS;

import com.google.gson.Gson;
import com.pucmm.practica4servidorconsumidor.Controller.SSEController;
import com.pucmm.practica4servidorconsumidor.Entitie.Sensor;
import com.pucmm.practica4servidorconsumidor.Repositorio.SensorRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static com.pucmm.practica4servidorconsumidor.Controller.ThymeleafController.COLA;

/**
 * Created by vacax on 04/10/15.
 */
public class Consumidor {

    ActiveMQConnectionFactory factory;
    Connection connection;
    Session session;
    Topic topic;
    MessageConsumer consumer;
    SensorRepository sensorRepository;

    public Consumidor(SensorRepository sensorRepository){
        this.sensorRepository = sensorRepository;
    }

    /**
     *
     */

    /**
     *
     * @throws JMSException
     */
    public void conectar() throws JMSException {
        //Creando el connection factory indicando el host y puerto, en la trama el failover indica que reconecta de manera
        // automatica
        factory = new ActiveMQConnectionFactory("admin", "admin", "failover:tcp://localhost:61616");
        //Crea un nuevo hilo cuando hacemos a conexión, que no se detiene cuando
        // aplicamos el metodo stop(), para eso tenemos que cerrar la JVM o
        // realizar un close().
        connection = factory.createConnection();
        // Arrancamos la conexión
        //Puede verlo en direccion por defecto de tu activemq local:
        //http://127.0.0.1:8161/admin/connections.jsp
        connection.start();
        // Creando una sesión no transaccional y automatica.
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // Creamos o nos connectamos a la una cola, por defecto ActiveMQ permite
        // la creación si no existe. Si la cola es del tipo Queue es acumula los mensajes, si es
        // del tipo topic es en el momento.
        topic = session.createTopic(COLA);
        consumer = session.createConsumer(topic);
        consumer.setMessageListener(message -> {
            try {
                TextMessage messageTexto = (TextMessage) message;
                System.out.println("El mensaje de texto recibido: " + messageTexto.getText());
                Sensor data = new Gson().fromJson(messageTexto.getText(), Sensor.class);
                sensorRepository.save(data);
                SSEController.dispacheEventToClientes(data);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }


    public void cerrarConexion() throws JMSException {
        connection.stop();
        connection.close();
    }
}
