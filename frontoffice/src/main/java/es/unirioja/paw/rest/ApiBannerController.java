/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.rest;

import es.unirioja.paw.jpa.ClienteEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Jarein
 */
@Controller
@RequestMapping("/api/banner")
public class ApiBannerController {

    @GetMapping(value = "/header",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String headerBanner(HttpServletRequest request, HttpSession session) {
        Locale locale = request.getLocale();
        String MES = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, locale);
        
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        
        if (cliente == null) {
            return "Envíos gratis en "+MES+" a partir de 130€";
        } else {
            Integer puntos = cliente.getPuntosFidelidad();
            if (puntos == null || puntos < 100) {
                return ("<b>Envíos gratis</b> en " + MES + " a partir de 100€").toUpperCase();
            } else if (puntos >= 100 && puntos <= 250) {
                return ("<b>Gracias por tu fidelidad!</b> Disfruta un 10% de descuento exclusivo para clientes frecuentes.").toUpperCase();
            } else {
                return ("<b>Gracias por tu fidelidad!</b> Disfruta un 15% de descuento exclusivo para clientes frecuentes.").toUpperCase();
            }
        }
    }
}