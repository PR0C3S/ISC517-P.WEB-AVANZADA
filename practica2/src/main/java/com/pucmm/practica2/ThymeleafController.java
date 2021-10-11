package com.pucmm.practica2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping(path="/practica2")
public class ThymeleafController {

    //Inyección de dependencia para la internacionalización
    @Autowired
    private MessageSource messageSource;

    @GetMapping(path ="/listarMock")
    public String getListaMock(Model model, Locale locale){

        //validar que el usuario esta logueado si no esta logueado se envia al login
        //si esta logueado crear un string llamado login con el user de el



        model.addAttribute("login","usuario"); //se manda usuario
        //i18n pasando parametros
        model.addAttribute("t1", messageSource.getMessage("t1", null, locale));
        model.addAttribute("t2", messageSource.getMessage("t2", null, locale));
        model.addAttribute("t3", messageSource.getMessage("t3", null, locale));
        model.addAttribute("t4", messageSource.getMessage("t4", null, locale));
        model.addAttribute("t5", messageSource.getMessage("t5", null, locale));
        model.addAttribute("bt1", messageSource.getMessage("bt1", null, locale));
        model.addAttribute("titulo", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav1", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav2", messageSource.getMessage("nav2", null, locale));
        model.addAttribute("nav3", messageSource.getMessage("nav3", null, locale));
        return "ListarMock";
    }


    @GetMapping(path ="/mock/new")
    public String getNuevoMock(Model model, Locale locale){

        //validar que el usuario esta logueado si no esta logueado se envia al login
        //si esta logueado crear un string llamado login con el user de el

        model.addAttribute("enlaceUser","http://localhost:8080/mock/"+"usuario"+"/view/"); // se manda esto para qe se vea bien
        model.addAttribute("login","usuario"); //se manda usuario
        //i18n pasando parametros
        model.addAttribute("nombre0", messageSource.getMessage("nombre0", null, locale));
        model.addAttribute("nombre1", messageSource.getMessage("nombre1", null, locale));
        model.addAttribute("nombre2", messageSource.getMessage("nombre2", null, locale));
        model.addAttribute("nombre3", messageSource.getMessage("nombre3", null, locale));
        model.addAttribute("nombre4", messageSource.getMessage("nombre4", null, locale));
        model.addAttribute("nombre5", messageSource.getMessage("nombre5", null, locale));
        model.addAttribute("nombre6", messageSource.getMessage("nombre6", null, locale));
        model.addAttribute("nombre7", messageSource.getMessage("nombre7", null, locale));
        model.addAttribute("nombre8", messageSource.getMessage("nombre8", null, locale));
        model.addAttribute("nombre9", messageSource.getMessage("nombre9", null, locale));
        model.addAttribute("nombre10", messageSource.getMessage("nombre10", null, locale));
        model.addAttribute("titulo", messageSource.getMessage("titulo", null, locale));
        model.addAttribute("nav1", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav2", messageSource.getMessage("nav2", null, locale));
        model.addAttribute("nav3", messageSource.getMessage("nav3", null, locale));


        return "Mock";
    }

}
