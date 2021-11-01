package com.pucmm.practica2.repositorios.seguridad;

import com.pucmm.practica2.entities.seguridad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, String> {
    Rol findByRol(String rol);
}