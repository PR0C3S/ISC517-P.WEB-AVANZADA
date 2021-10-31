package com.pucmm.practica3;

import com.pucmm.practica3.services.seguridad.SeguridadServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Practica3Application {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(Practica3Application.class, args);

        SeguridadServices seguridadServices = (SeguridadServices) applicationContext.getBean("seguridadServices");
        seguridadServices.createAdminUser();
    }


}
