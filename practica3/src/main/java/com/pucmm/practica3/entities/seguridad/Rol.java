package com.pucmm.practica3.entities.seguridad;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Rol implements Serializable {

    //Atributos
    @Id
    private
    String rol;

    //Constructor
    public Rol(){

    }

    public Rol(String rol){
        this.rol = rol;
    }

    //Setters y Getters
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}
