package com.pucmm.practica3.repositorios.seguridad;

import com.pucmm.practica3.entities.seguridad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, String> {
    Rol findByRol(String rol);
}