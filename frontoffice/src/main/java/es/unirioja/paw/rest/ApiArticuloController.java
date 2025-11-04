/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.rest;

import es.unirioja.paw.jpa.OfertaEntity;
import es.unirioja.paw.repository.OfertaArticuloRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Jarein
 */
@Controller
@RequestMapping("/api/articulo")
public class ApiArticuloController {

    @Autowired
    private OfertaArticuloRepository ofertaArticuloRepository;

    @GetMapping(value = "/{articleId}/oferta", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> getOferta(@PathVariable("articleId") String articleId) {
        try {
            OfertaEntity oferta = ofertaArticuloRepository.findById(articleId).orElse(null);

            if (oferta == null || oferta.getActiva() == null || oferta.getActiva() != 1) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }

            return ResponseEntity.ok(oferta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
}
