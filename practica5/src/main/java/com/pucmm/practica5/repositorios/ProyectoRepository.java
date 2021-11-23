package com.pucmm.practica5.repositorios;

import com.pucmm.practica5.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    @Query("select u from Proyecto u where u.usuario = ?1")
    Proyecto consultaProyecto(String usuario);


}
