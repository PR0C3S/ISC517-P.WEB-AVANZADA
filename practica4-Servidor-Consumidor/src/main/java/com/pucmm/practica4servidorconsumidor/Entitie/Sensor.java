package com.pucmm.practica4servidorconsumidor.Entitie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sensor implements Serializable {
    private int idDispositivo;
    private int temperatura;
    private int humedad;
    @Id
    private String fechaGeneracion;
    public Sensor(String idDispositivo){
        this.temperatura = (int) (Math.random() * 100) + 1;
        this.humedad = (int) (Math.random() * 100) + 1;
        this.fechaGeneracion = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        this.idDispositivo = Integer.parseInt(idDispositivo);
    }

}
