/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Jarein
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
    @GetMapping("/paw-2025")
    public String paw2025(Model model) {
        model.addAttribute("titulo", "PAW 2025");
        model.addAttribute("contenido", "Contenido exclusivo sobre PAW 2025 😎");
        return "plantilla_blog";
    }

    @GetMapping("/tendencias-decorativas")
    public String tendencias(Model model) {
        model.addAttribute("titulo", "Tendencias Decorativas 2025");
        model.addAttribute("contenido", "Las últimas tendencias en decoración serán las siguientes:");
        return "plantilla_blog";
    }
    @GetMapping("/consejos-renovar-casa")
    public String consejos(Model model) {
        model.addAttribute("titulo", "Consejos para renovar la casa");
        model.addAttribute("contenido", "Os presentamos los consejos de esta temporada para renovar la casa:");
        return "plantilla_blog";
    }
    @GetMapping("/semana-verde")
    public String semana_verde(Model model) {
        model.addAttribute("titulo", "Semana Verde");
        model.addAttribute("contenido", "¡La semana verde ha llegado a Electrosa!");
        return "plantilla_blog";
    }
}
