package com.pucmm.practica4_publicador.main;

import com.google.gson.Gson;
import com.pucmm.practica4_publicador.jms.Productor;
import com.pucmm.practica4_publicador.Sensor;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Ejemplo para probar el uso de JMS, utilizando servidor de mensajeria ActiveMQ, ver en
 * http://activemq.apache.org/
 * Created by vacax on 03/10/15.
 */
public class Main {

    public static void main(String[] args) throws IOException, JMSException  {
        System.out.println("Prueba de Mensajeria Asincrona");

        if(args.length == 0){
            mensajesParametros();
            System.out.println("No se recibieron parametros, finalizando la app...");
            return;
        }

        int opcion = Integer.parseInt(args[0]);
        if(opcion == 1 || opcion == 2){
            Productor publicador = new Productor();
            Thread hilo = new Thread(new Runnable() {
                public void run() {
                    while(true) {
                        Gson gson = new Gson();
                        try {
                            publicador.enviarMensaje(gson.toJson(new Sensor(opcion)));
                            System.out.println("Mensaje enviado");
                            System.out.println("Presiona Enter para salir del programa:");
                            Thread.sleep(60000);
                        } catch (JMSException | InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            });
            hilo.start();
           while(true){}
        }
        else{
            mensajesParametros();
        }

    }

    /**
     * Mostrando opciones disponible
     */
    private static void mensajesParametros(){
        System.out.println("Deben enviar los parametros: # de productor que desea crear");
        System.out.println("Si modo-aplicacion == 1, crear productor dispositvo 1 - tipo Cola Topic");
        System.out.println("Si modo-aplicacion == 2, crear productor dispositvo 2 - tipo Cola Topic");
    }
}
