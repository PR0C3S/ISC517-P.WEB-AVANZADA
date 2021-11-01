package com.pucmm.practica3.services.seguridad;

import com.pucmm.practica3.entities.Proyecto;
import com.pucmm.practica3.entities.seguridad.Rol;
import com.pucmm.practica3.entities.seguridad.Usuario;
import com.pucmm.practica3.repositorios.ProyectoRepository;
import com.pucmm.practica3.repositorios.seguridad.RolRepository;
import com.pucmm.practica3.repositorios.seguridad.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SeguridadServices implements UserDetailsService {

    //Atributos
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ProyectoRepository proyectoRepository;

    //Atributo para encriptar la informacion
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    //Constructor
    public SeguridadServices(UsuarioRepository usuarioRepository, RolRepository rolRepository, ProyectoRepository proyectoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public void createAdminUser(){

        if(usuarioRepository.findByUsername("admin") == null)
        {
            System.out.println("USUARIO ADMIN CREADO");
            Rol Admin = new Rol("ROLE_ADMIN");
            Rol User = new Rol("ROLE_USER");
            rolRepository.save(Admin);
            Usuario admin = new Usuario();
            admin.setNombre("admin");
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            admin.setRoles(new HashSet<>(List.of(Admin,User)));

            Proyecto proyecto = new Proyecto();
            proyecto.setUsuario(admin);

            admin.setProyecto(proyecto);
            usuarioRepository.save(admin);

            //
            //proyectoRepository.save(proyecto);
            return;
        }
        System.out.println("YA EXISTIA USUARIO ADMIN");
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
