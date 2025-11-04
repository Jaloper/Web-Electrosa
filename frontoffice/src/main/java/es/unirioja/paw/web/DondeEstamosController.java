/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import es.unirioja.paw.jpa.AlmacenEntity;
import es.unirioja.paw.repository.AlmacenRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Jarein
 */
@Controller
@RequestMapping("/info/donde-estamos")
public class DondeEstamosController {
 @Autowired
    private AlmacenRepository almacenRepository;
 
 @GetMapping
    public String mapaAlmacenes(
            Model model) {

        List<AlmacenEntity> almacenes = almacenRepository.findAll();         
        model.addAttribute("almacenes", almacenes);

        return "almacenes-map";
    }
}
