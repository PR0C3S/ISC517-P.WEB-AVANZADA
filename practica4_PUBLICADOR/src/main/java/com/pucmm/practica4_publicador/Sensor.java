package com.pucmm.practica4_publicador;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Sensor  {
    private int idDispositivo;
    private int temperatura;
    private int humedad;

    private String fechaGeneracion;
    public Sensor(String idDispositivo){
        this.temperatura = (int) (Math.random() * 100) + 1;
        this.humedad = (int) (Math.random() * 100) + 1;
        this.fechaGeneracion = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        this.idDispositivo = Integer.parseInt(idDispositivo);
    }

}
