package com.pucmm.practica2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/practica2")
public class VelocityController {

    @GetMapping(path ="/mock/new")
    public String getNuevoEstudiante(Model model){
        model.addAttribute("titulo","Nuevo Mock");
        model.addAttribute("check",true); //siempre esta logeado se manda eso para ocultar nav
        model.addAttribute("enlaceUser","http://localhost:8080/mock/"+"usuario"+"/view/"); // se manda esto para qe se vea bien
        model.addAttribute("login","usuario"); //se manda usuario
        return "Mock";
    }

}
