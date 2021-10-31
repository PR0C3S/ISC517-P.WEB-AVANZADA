package com.pucmm.practica3.repositorios.seguridad;

import com.pucmm.practica3.entities.seguridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByUsername(String username);



}