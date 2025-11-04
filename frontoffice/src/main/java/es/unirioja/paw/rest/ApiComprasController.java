/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.rest;

import com.github.javafaker.Faker;
import es.unirioja.paw.jpa.ArticuloEntity;
import es.unirioja.paw.repository.ArticuloRepository;
import java.util.Locale;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Jarein
 */

@Controller
@RequestMapping("/api/compras")
public class ApiComprasController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Faker faker = new Faker(Locale.of("es"));

    @Autowired
    private ArticuloRepository articuloRepository;

    @GetMapping("/ultimo-articulo")
    @ResponseBody
    public ArticuloEntity randomArticulo() {
        long count = articuloRepository.count();
        
        if (count == 0) {
            logger.warn("No hay artículos disponibles");
            return null;
        }

        int pageNumber = faker.number().numberBetween(0, (int) count);
        Pageable pageable = PageRequest.of(pageNumber, 1);
        Page<ArticuloEntity> articuloPage = articuloRepository.findAll(pageable);

        return Optional.ofNullable(articuloPage.getContent())
                      .filter(list -> !list.isEmpty())
                      .map(list -> list.get(0))
                      .orElse(null);
    }
}