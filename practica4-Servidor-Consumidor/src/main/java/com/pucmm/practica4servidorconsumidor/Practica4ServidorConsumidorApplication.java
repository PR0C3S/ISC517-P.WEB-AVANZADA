package com.pucmm.practica4servidorconsumidor;

import org.apache.activemq.broker.BrokerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Practica4ServidorConsumidorApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Practica4ServidorConsumidorApplication.class, args);
        System.out.println("Inicializando Servidor JMS");
        try {
            //Subiendo la versión embedded de ActiveMQ.
            //http://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection.html
            BrokerService broker = new BrokerService();
            //configurando el broker.
            broker.addConnector("tcp://0.0.0.0:61616");
            //Inicializando
            broker.start();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Error: "+ex.getMessage());
        }

        System.out.println("Aplicacion corriendo correctamente");
    }

}
