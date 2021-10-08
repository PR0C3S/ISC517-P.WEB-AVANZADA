package com.pucmm.practica1.Controladores;

import com.pucmm.practica1.Entidades.Estudiante;
import com.pucmm.practica1.Services.EstudianteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/estudiante")
public class ThymeleafController {

    private final EstudianteService estudianteService = new EstudianteService();

    @GetMapping
    public String getListaEstudiantes(Model model){
        model.addAttribute("listaEstudiante",estudianteService.getListaEstudiantes());
        model.addAttribute("titulo","Listar estudiante");
        return "ListarEstudiante";
    }


    @GetMapping(path ="/nuevo")
    public String getNuevoEstudiante(Model model){
        model.addAttribute("titulo","Crear estudiante");
        model.addAttribute("action","New");
        model.addAttribute("estudiante",new Estudiante());
        return "Estudiante";
    }


    @PostMapping(path ="/nuevo/save")
    public String postEstudiante(Model model,
                                 @RequestParam("matricula") int matricula,
                                 @RequestParam("nombre") String nombre,
                                 @RequestParam("apellido") String apellido,
                                 @RequestParam("telefono") String telefono)
    {
        Boolean accion = estudianteService.crearEstudiante(nombre,apellido,telefono,matricula);
        if(!accion)
        {
            model.addAttribute("titulo","Error");
            model.addAttribute("mensaje","Error matricula en uso");
            return "Error";
        }

        model.addAttribute("listaEstudiante",estudianteService.getListaEstudiantes());
        model.addAttribute("titulo","Listar estudiante");
        return "ListarEstudiante";
    }


    @PostMapping (path = "/update/{matricula}")
    public String putEstudiante(Model model, @PathVariable int matricula,
                                @RequestParam("nombre") String nombre,
                                @RequestParam("apellido") String apellido,
                                @RequestParam("telefono") String telefono) {

        Boolean accion = estudianteService.updateEstudiante(nombre,apellido,telefono,matricula);
        if(!accion) {

            model.addAttribute("titulo","Error");
            model.addAttribute("mensaje","Error 404, estudiante no encontrado");
            return "Error";

        }

        model.addAttribute("listaEstudiante",estudianteService.getListaEstudiantes());
        model.addAttribute("titulo","Listar estudiante");
        return "ListarEstudiante";
    }


    @PostMapping(path ="/delete/{matricula}")
    public String deleteEstudiante(Model model,
                                   @PathVariable int matricula)
    {
        Boolean accion =estudianteService.borrarEstudiante(matricula);
        if(!accion)
        {
            model.addAttribute("titulo","Error");
            model.addAttribute("mensaje","Error 404, Estudiante no encontrado");
            return "Error";

        }

        model.addAttribute("listaEstudiante",estudianteService.getListaEstudiantes());
        model.addAttribute("titulo","Listar estudiante");
        return "ListarEstudiante";
    }


    @GetMapping(path ="/ver/{matricula}")
    public String getEstudiante(Model model,@PathVariable int matricula){
        Estudiante act = estudianteService.buscarEstudiante(matricula);
        if(act==null)
        {
            model.addAttribute("titulo","Error");
            model.addAttribute("mensaje","Error 404, Estudiante no encontrado");
            return "Error";
        }
        model.addAttribute("titulo","Editar estudiante: "+matricula);
        model.addAttribute("estudiante",act);
        model.addAttribute("action","Edit");
        return "Estudiante";
    }

}
