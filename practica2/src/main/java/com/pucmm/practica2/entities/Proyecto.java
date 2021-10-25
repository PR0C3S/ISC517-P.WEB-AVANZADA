package com.pucmm.practica2.entities;

import com.pucmm.practica2.entities.seguridad.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private Usuario usuario;

    //Relacion con clase Mock.
    @OneToMany(mappedBy="proyecto")
    private Set<Mock> mocks = new HashSet<Mock>();
}
