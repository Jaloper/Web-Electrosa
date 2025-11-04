/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.LineaCestaCompraEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class CestaGlobal {
    // Añade siempre al modelo la lista de líneas de la cesta 
     @ModelAttribute("lineasCesta")
    public List<LineaCestaCompraEntity> lineasCesta(HttpSession session) {
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");
        return (cesta != null && cesta.getLineas() != null) ? cesta.getLineas(): Collections.emptyList();
    }
}

