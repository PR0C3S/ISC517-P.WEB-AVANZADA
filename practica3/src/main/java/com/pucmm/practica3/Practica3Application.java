package com.pucmm.practica3;

import com.pucmm.practica3.services.seguridad.SeguridadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
@SpringBootApplication
public class Practica3Application implements CommandLineRunner {
    @Autowired
    private Environment environment;


    public static void main(String[] args) throws InterruptedException{

        Thread.sleep(5000);
        ApplicationContext applicationContext = SpringApplication.run(Practica3Application.class, args);

        SeguridadServices seguridadServices = (SeguridadServices) applicationContext.getBean("seguridadServices");
        seguridadServices.createAdminUser();
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
