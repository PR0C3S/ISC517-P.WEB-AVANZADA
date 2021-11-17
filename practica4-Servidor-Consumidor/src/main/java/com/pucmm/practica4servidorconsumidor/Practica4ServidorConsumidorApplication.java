package com.pucmm.practica4servidorconsumidor;

import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Practica4ServidorConsumidorApplication implements CommandLineRunner{
    @Autowired
    private Environment environment;
    public static void main(String[] args) throws InterruptedException{
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

    @Override
    public void run(String... args) throws Exception {
        //leyendo la información de las variables.
        String db_nombre = environment.getProperty("NOMBRE_APP");
        String direccionDb = environment.getProperty("DB_HOST");
        System.out.println("Nombre de la Aplicación = "+db_nombre);
        System.out.println("Dirección de la Aplicación = "+direccionDb);
    }

}
