package com.pucmm.practica2.repositorios;

import com.pucmm.practica2.entities.Mock;
import com.pucmm.practica2.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    @Query("select u from Proyecto u where u.usuario = ?1")
    Proyecto consultaProyecto(String usuario);


}
