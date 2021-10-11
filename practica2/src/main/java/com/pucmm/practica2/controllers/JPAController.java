package com.pucmm.practica2.controllers;

import com.pucmm.practica2.entities.Mock;
import com.pucmm.practica2.services.MockServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpa")
public class JPAController {

    final MockServices mockServices;

    public JPAController(MockServices mockServices) {
        this.mockServices = mockServices;
    }

    @RequestMapping("/mockByID/{id}")
    public Mock mockByID(@PathVariable Long id){
        return mockServices.mockByID(id);
    }
}
