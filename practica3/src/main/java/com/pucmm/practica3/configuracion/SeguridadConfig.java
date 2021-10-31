package com.pucmm.practica3.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SeguridadConfig extends WebSecurityConfigurerAdapter {

    //Opción JPA
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        //Clase para encriptar contraseña
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        //Configuración JPA.
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Marcando las reglas para permitir unicamente los usuarios
        http
                .authorizeRequests()
                .antMatchers("/practica2/**").authenticated()//hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin()
                .loginPage("/practica2/login") //indicando la ruta que estaremos utilizando.
                .defaultSuccessUrl("/practica2/listarMock") //ruta por defecto a redireccion si no hay ninguna
                .failureUrl("/practica2/login?error") //en caso de fallar puedo indicar otra pagina.
                .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/practica2/login")//cierre de sesion
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/practica2/mock/view/*").permitAll()
                .and().addFilterBefore(new JWTAutorizacionFilter(), BasicAuthenticationFilter.class);
//


        //permitiendo el acceso vía cors, crfs y los iframe.
        http.cors().disable();
        http.csrf().disable();
        http.headers().frameOptions().disable();

   }

}
