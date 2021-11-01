package com.pucmm.practica2.services.seguridad;

import com.pucmm.practica2.entities.seguridad.Rol;
import com.pucmm.practica2.entities.seguridad.Usuario;
import com.pucmm.practica2.repositorios.seguridad.RolRepository;
import com.pucmm.practica2.repositorios.seguridad.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeguridadServices implements UserDetailsService {

    //Atributos
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    //Atributo para encriptar la informacion
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    //Constructor
    public SeguridadServices(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public void createAdminUser(){
        Rol Admin = new Rol("ROLE_ADMIN");
        Rol User = new Rol("ROLE_USER");
        rolRepository.save(Admin);

        Usuario admin = new Usuario();
        admin.setNombre("admin");
        admin.setUsername("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("admin"));
        admin.setRoles(new HashSet<>(List.of(Admin,User)));
        usuarioRepository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByUsername(username);

        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Rol role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRol()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }


}
