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
    private boolean admin = true;

    //Relacion con clase Rol.
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rol> roles = new HashSet<Rol>();

    //Relacion con clase Mock.
    @OneToMany
    private Set<Mock> mocks = new HashSet<Mock>();

}
