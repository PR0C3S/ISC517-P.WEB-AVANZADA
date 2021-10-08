package com.pucmm.practica1.Services;

import com.pucmm.practica1.*;
import com.pucmm.practica1.Entidades.Estudiante;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class EstudianteService {

    private ArrayList<Estudiante> listaEstudiantes= new ArrayList<Estudiante>();

    public Boolean crearEstudiante(String nombre, String apellido, String telefono, int matricula)
    {
        if(buscarEstudiante(matricula)==null)
        {
            Estudiante act = new Estudiante();
            act.setNombre(nombre);
            act.setApellido(apellido);
            act.setMatricula(matricula);
            act.setTelefono(telefono);
            listaEstudiantes.add(act);
            return true;
        }
        return  false;
    }

    public Boolean borrarEstudiante(int matricula)
    {
        for (Estudiante act: listaEstudiantes) {
            if(act.getMatricula() == matricula)
            {
                listaEstudiantes.remove(act);
                return true;
            }
        }
        return  false;
    }

    public Boolean updateEstudiante(String nombre, String apellido, String telefono, int matricula)
    {
        for (int i=0; i<listaEstudiantes.size(); i++)
        {
            if(listaEstudiantes.get(i).getMatricula() == matricula)
            {
                listaEstudiantes.get(i).setTelefono(telefono);
                listaEstudiantes.get(i).setNombre(nombre);
                listaEstudiantes.get(i).setApellido(apellido);
                return true;
            }
        }
        return  false;
    }

    public Estudiante buscarEstudiante(int matricula)
    {
        for (Estudiante act: listaEstudiantes)
        {
            if(act.getMatricula() == matricula)
            {
                return act;
            }
        }
        return null;
    }
}
