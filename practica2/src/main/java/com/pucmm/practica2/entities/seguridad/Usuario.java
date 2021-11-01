package com.pucmm.practica2.entities.seguridad;

import com.pucmm.practica2.entities.Mock;
import com.pucmm.practica2.entities.Proyecto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter

public class Usuario implements Serializable {

    //Atributos
    @Id
    private String username;
    private String password;
    private String nombre;
    private Boolean isActivo= true;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Proyecto proyecto;

    //Relacion con clase Rol.
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rol> roles = new HashSet<Rol>();

    public Boolean isAdmin()
    {
        for (Rol act: roles) {
            if(act.getRol().equals("ROLE_ADMIN"))
            {
                return true;
            }
        }

        return false;
    }

}
