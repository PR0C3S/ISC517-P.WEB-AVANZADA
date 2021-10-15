package com.pucmm.practica2.entities.seguridad;

import com.pucmm.practica2.entities.Mock;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Usuario implements Serializable {

    //Atributos
    @Id
    private String username;
    private String password;
    private String nombre;

    //Relacion con clase Rol.
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rol> roles = new HashSet<Rol>();

    //Relacion con clase Mock.
    @OneToMany
    private Set<Mock> mocks = new HashSet<Mock>();

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
