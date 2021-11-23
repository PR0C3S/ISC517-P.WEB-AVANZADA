package com.pucmm.practica5.repositorios.seguridad;

import com.pucmm.practica5.entities.seguridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByUsername(String username);



}