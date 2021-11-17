package com.pucmm.practica4servidorconsumidor.Repositorio;

import com.pucmm.practica4servidorconsumidor.Entitie.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SensorRepository extends JpaRepository<Sensor, String> {

    @Query("select u from Sensor u where u.idDispositivo = ?1")
    List<Sensor> getListaByID(int id);
}