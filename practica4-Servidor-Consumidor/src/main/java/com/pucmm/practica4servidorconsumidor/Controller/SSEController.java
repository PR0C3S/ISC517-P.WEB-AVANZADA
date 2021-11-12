package com.pucmm.practica4servidorconsumidor.Controller;

import com.pucmm.practica4servidorconsumidor.Entitie.Sensor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@Component
public class SSEController {

    public static List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/suscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter suscbribe()
    {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try{
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e){
            e.printStackTrace();
        }
        sseEmitter.onCompletion(()->emitters.remove(sseEmitter));
        emitters.add(sseEmitter);
        return sseEmitter;
    }

    public static void dispacheEventToClientes(Sensor data){
        String evento = "";
        JSONObject eventFormmated = new JSONObject();

        if(data.getIdDispositivo() == 1)
        {
            evento = "Dispositivo 1";
        }else{
            evento = "Dispositivo 2";
        }
        eventFormmated.put("temperatura", data.getTemperatura());
        eventFormmated.put("humedad",data.getHumedad());
        eventFormmated.put("fecha", data.getFechaGeneracion());

        for (SseEmitter emiter: emitters) {
            try{
                emiter.send(SseEmitter.event().name(evento).data(eventFormmated.toString()));
            } catch (IOException e) {
                emitters.remove(emiter);
            }
        }
    }

}