package com.pucmm.practica4servidorconsumidor.Controller;

import com.pucmm.practica4servidorconsumidor.JMS.Consumidor;
import com.pucmm.practica4servidorconsumidor.Entitie.Sensor;
import com.pucmm.practica4servidorconsumidor.Repositorio.SensorRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Component
@RequestMapping(path="/practica4")
public class ThymeleafController {

    @Autowired
    private SensorRepository sensorRepository;
    public static String COLA = "notificacion_sensores";

    @GetMapping (path = "/consumidor")
    public String getGraficos(Model model) throws InterruptedException, JMSException {
        //indicando el consumidor con la cola.
        Consumidor consumidor= new Consumidor(sensorRepository);
        consumidor.conectar();
        System.out.println("Consumidor conectado");
        return "Consumidor";
    }

    @GetMapping (path = "/getdata", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDataGraficos(@RequestParam("data") String data) throws JSONException {
        JSONObject eventFormmated = new JSONObject();
        List<String> fechas = new ArrayList<>();
        List<Integer> humedad = new ArrayList<>();
        List<Integer> temperatura = new ArrayList<>();
        List<Sensor> sensorLista = new ArrayList<>();

        if(data.equals("1")){
            sensorLista = sensorRepository.getListaByID(1);
        }else{
            sensorLista = sensorRepository.getListaByID(2);
        }

        for (Sensor act: sensorLista) {
            fechas.add(act.getFechaGeneracion());
            humedad.add(act.getHumedad());
            temperatura.add(act.getTemperatura());
        }

        eventFormmated.put("temperatura", temperatura);
        eventFormmated.put("humedad",humedad);
        eventFormmated.put("fecha", fechas);
        return ResponseEntity.ok(eventFormmated.toString());
    }



}
